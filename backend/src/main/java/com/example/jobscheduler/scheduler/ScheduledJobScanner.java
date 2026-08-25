package com.example.jobscheduler.scheduler;

import com.example.jobscheduler.entity.JobEntity;
import com.example.jobscheduler.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
public class ScheduledJobScanner {

    private final JobRepository jobRepository;

    public ScheduledJobScanner(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void scanAndQueue() {
        List<JobEntity> due = jobRepository.findScheduledDue(Instant.now());
        for (JobEntity j : due) {
            j.setStatus("QUEUED");
            jobRepository.save(j);
        }
    }
}
