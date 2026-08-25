package com.example.jobscheduler.controller;

import com.example.jobscheduler.entity.Worker;
import com.example.jobscheduler.repository.WorkerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerRepository workerRepository;

    public WorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> body) {
        String workerId = (String) body.get("workerId");
        String hostname = (String) body.get("hostname");
        Integer capacity = (Integer) body.getOrDefault("capacity", 1);

        var existing = workerRepository.findByWorkerId(workerId);
        Worker w;
        if (existing.isPresent()) {
            w = existing.get();
            w.setHostname(hostname);
            w.setCapacity(capacity);
            w.setLastSeen(Instant.now());
        } else {
            w = new Worker(null, workerId, hostname, capacity, Instant.now());
        }
        workerRepository.save(w);
        return ResponseEntity.ok(Map.of("id", w.getId()));
    }

    @PostMapping("/heartbeat")
    public ResponseEntity<?> heartbeat(@RequestBody Map<String, Object> body) {
        String workerId = (String) body.get("workerId");
        var existing = workerRepository.findByWorkerId(workerId);
        if (existing.isEmpty()) return ResponseEntity.badRequest().body("unknown worker");
        Worker w = existing.get();
        w.setLastSeen(Instant.now());
        workerRepository.save(w);
        return ResponseEntity.ok().build();
    }
}
