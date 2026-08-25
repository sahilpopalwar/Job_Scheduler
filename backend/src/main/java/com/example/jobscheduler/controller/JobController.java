package com.example.jobscheduler.controller;

import com.example.jobscheduler.entity.JobEntity;
import com.example.jobscheduler.repository.JobRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @PostMapping
    public ResponseEntity<JobEntity> create(@RequestBody JobEntity job) {
        job.setStatus("QUEUED");
        job.setAttempts(0);
        JobEntity saved = jobRepository.save(job);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/delayed")
    public ResponseEntity<JobEntity> createDelayed(@RequestBody JobEntity job) {
        job.setStatus("SCHEDULED");
        if (job.getScheduledTime() == null) job.setScheduledTime(Instant.now().plusSeconds(60));
        JobEntity saved = jobRepository.save(job);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<JobEntity>> list(@RequestParam(required = false) String status) {
        if (status != null) return ResponseEntity.ok(jobRepository.findByStatus(status));
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @GetMapping("/scheduled/due")
    public ResponseEntity<List<JobEntity>> scheduledDue() {
        return ResponseEntity.ok(jobRepository.findScheduledDue(Instant.now()));
    }
}
