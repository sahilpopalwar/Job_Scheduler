package com.example.jobscheduler.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "worker_heartbeats")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "worker_id")
    private String workerId;

    private String hostname;

    private Integer capacity;

    @Column(name = "last_seen")
    private Instant lastSeen;

    public Worker() {}

    public Worker(Long id, String workerId, String hostname, Integer capacity, Instant lastSeen) {
        this.id = id;
        this.workerId = workerId;
        this.hostname = hostname;
        this.capacity = capacity;
        this.lastSeen = lastSeen;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWorkerId() { return workerId; }
    public void setWorkerId(String workerId) { this.workerId = workerId; }

    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Instant getLastSeen() { return lastSeen; }
    public void setLastSeen(Instant lastSeen) { this.lastSeen = lastSeen; }
}
