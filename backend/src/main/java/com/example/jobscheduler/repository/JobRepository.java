package com.example.jobscheduler.repository;

import com.example.jobscheduler.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
    List<JobEntity> findByStatus(String status);

    List<JobEntity> findByQueue_IdAndStatus(Long queueId, String status);

    @Query("select j from JobEntity j where j.scheduledTime <= :now and j.status = 'SCHEDULED'")
    List<JobEntity> findScheduledDue(@Param("now") Instant now);
}
