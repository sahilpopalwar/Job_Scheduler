package com.example.jobscheduler.repository;

import com.example.jobscheduler.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findByWorkerId(String workerId);
}
