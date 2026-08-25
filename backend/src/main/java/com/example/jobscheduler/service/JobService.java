package com.example.jobscheduler.service;

import com.example.jobscheduler.entity.JobEntity;
import com.example.jobscheduler.repository.JobRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class JobService {

    private final JdbcTemplate jdbcTemplate;
    private final JobRepository jobRepository;

    public JobService(JdbcTemplate jdbcTemplate, JobRepository jobRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.jobRepository = jobRepository;
    }

    @Transactional
    public Optional<JobEntity> claimOneJob() {
        // Use FOR UPDATE SKIP LOCKED to atomically pick one queued job
        String selectSql = "SELECT id FROM jobs WHERE status = 'QUEUED' ORDER BY priority NULLS LAST, scheduled_time ASC LIMIT 1 FOR UPDATE SKIP LOCKED";
        try {
            Long id = jdbcTemplate.queryForObject(selectSql, Long.class);
            if (id == null) return Optional.empty();
            jdbcTemplate.update("UPDATE jobs SET status = 'CLAIMED' WHERE id = ?", id);
            return jobRepository.findById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Transactional
    public void markRunning(Long id, String workerId) {
        jdbcTemplate.update("UPDATE jobs SET status = 'RUNNING' WHERE id = ?", id);
        jdbcTemplate.update("INSERT INTO job_executions (job_id, worker_id, start_time, status) VALUES (?,?,?,?)", id, workerId, Timestamp.from(Instant.now()), "RUNNING");
    }

    @Transactional
    public void markCompleted(Long id) {
        jdbcTemplate.update("UPDATE jobs SET status = 'COMPLETED' WHERE id = ?", id);
        jdbcTemplate.update("UPDATE job_executions SET end_time = ?, status = ? WHERE job_id = ? AND status = ?", Timestamp.from(Instant.now()), "COMPLETED", id, "RUNNING");
    }

    @Transactional
    public void markFailed(Long id, String reason) {
        jdbcTemplate.update("UPDATE jobs SET status = 'FAILED' WHERE id = ?", id);
        jdbcTemplate.update("UPDATE job_executions SET end_time = ?, status = ? WHERE job_id = ? AND status = ?", Timestamp.from(Instant.now()), "FAILED", id, "RUNNING");
        jdbcTemplate.update("INSERT INTO dead_letter_queue (job_id, reason, created_at) VALUES (?,?,?)", id, reason, Timestamp.from(Instant.now()));
    }
}
