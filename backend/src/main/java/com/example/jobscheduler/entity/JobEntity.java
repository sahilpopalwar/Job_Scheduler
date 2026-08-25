package com.example.jobscheduler.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "jobs")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private QueueEntity queue;

    @Column(nullable = false)
    private String status; // QUEUED,SCHEDULED,CLAIMED,RUNNING,COMPLETED,FAILED,DEAD_LETTER

    private Integer priority;

    private Instant scheduledTime;

    private Instant nextRetryTime;

    private Integer attempts;

    private Integer maxAttempts;

    @Lob
    private String payload; // JSON payload for worker

    public JobEntity() {}

    public JobEntity(Long id, String name, QueueEntity queue, String status, Integer priority, Instant scheduledTime, Instant nextRetryTime, Integer attempts, Integer maxAttempts, String payload) {
        this.id = id;
        this.name = name;
        this.queue = queue;
        this.status = status;
        this.priority = priority;
        this.scheduledTime = scheduledTime;
        this.nextRetryTime = nextRetryTime;
        this.attempts = attempts;
        this.maxAttempts = maxAttempts;
        this.payload = payload;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public QueueEntity getQueue() { return queue; }
    public void setQueue(QueueEntity queue) { this.queue = queue; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public Instant getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(Instant scheduledTime) { this.scheduledTime = scheduledTime; }

    public Instant getNextRetryTime() { return nextRetryTime; }
    public void setNextRetryTime(Instant nextRetryTime) { this.nextRetryTime = nextRetryTime; }

    public Integer getAttempts() { return attempts; }
    public void setAttempts(Integer attempts) { this.attempts = attempts; }

    public Integer getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
}
