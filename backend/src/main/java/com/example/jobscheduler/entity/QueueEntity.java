package com.example.jobscheduler.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "queues")
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer priority; // lower number = higher priority

    private Integer concurrencyLimit;

    private String status; // ACTIVE, PAUSED

    private String retryPolicy;

    public QueueEntity() {}

    public QueueEntity(Long id, String name, Integer priority, Integer concurrencyLimit, String status, String retryPolicy) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.concurrencyLimit = concurrencyLimit;
        this.status = status;
        this.retryPolicy = retryPolicy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public Integer getConcurrencyLimit() { return concurrencyLimit; }
    public void setConcurrencyLimit(Integer concurrencyLimit) { this.concurrencyLimit = concurrencyLimit; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRetryPolicy() { return retryPolicy; }
    public void setRetryPolicy(String retryPolicy) { this.retryPolicy = retryPolicy; }
}
