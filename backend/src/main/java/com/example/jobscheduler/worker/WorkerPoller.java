package com.example.jobscheduler.worker;

import com.example.jobscheduler.entity.JobEntity;
import com.example.jobscheduler.service.JobService;
import com.example.jobscheduler.repository.WorkerRepository;
import com.example.jobscheduler.entity.Worker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class WorkerPoller {

    private final JobService jobService;
    private final WorkerRepository workerRepository;
    private final String workerId = "worker-" + UUID.randomUUID().toString();
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

    public WorkerPoller(JobService jobService, WorkerRepository workerRepository) {
        this.jobService = jobService;
        this.workerRepository = workerRepository;
        registerWorker();
    }

    private void registerWorker() {
        Worker w = new Worker(null, workerId, "localhost", 4, Instant.now());
        workerRepository.save(w);
    }

    @Scheduled(fixedDelay = 5000)
    public void poll() {
        // heartbeat
        var w = workerRepository.findByWorkerId(workerId);
        if (w.isPresent()) {
            Worker worker = w.get();
            worker.setLastSeen(Instant.now());
            workerRepository.save(worker);
        }

        Optional<JobEntity> maybe = jobService.claimOneJob();
        if (maybe.isPresent()) {
            JobEntity job = maybe.get();
            pool.submit(() -> execute(job));
        }
    }

    private void execute(JobEntity job) {
        try {
            jobService.markRunning(job.getId(), workerId);
            // Simple dummy execution: sleep according to payload or fixed
            Thread.sleep(2000);
            jobService.markCompleted(job.getId());
        } catch (Exception ex) {
            jobService.markFailed(job.getId(), ex.getMessage());
        }
    }
}
