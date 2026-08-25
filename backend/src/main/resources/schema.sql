-- Minimal schema for Job Scheduler

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  role VARCHAR(50),
  created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS organizations (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT
);

CREATE TABLE IF NOT EXISTS projects (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  organization_id INT REFERENCES organizations(id)
);

CREATE TABLE IF NOT EXISTS queues (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  priority INT,
  concurrency_limit INT,
  retry_policy VARCHAR(255),
  status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS jobs (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  queue_id INT REFERENCES queues(id),
  status VARCHAR(50),
  priority INT,
  scheduled_time TIMESTAMP,
  next_retry_time TIMESTAMP,
  attempts INT DEFAULT 0,
  max_attempts INT DEFAULT 5,
  payload TEXT
);

CREATE TABLE IF NOT EXISTS job_executions (
  id SERIAL PRIMARY KEY,
  job_id INT REFERENCES jobs(id),
  worker_id VARCHAR(255),
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  status VARCHAR(50),
  execution_time_ms INT
);

CREATE TABLE IF NOT EXISTS dead_letter_queue (
  id SERIAL PRIMARY KEY,
  job_id INT,
  reason TEXT,
  created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS worker_heartbeats (
  id SERIAL PRIMARY KEY,
  worker_id VARCHAR(255),
  hostname VARCHAR(255),
  capacity INT,
  last_seen TIMESTAMP
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_jobs_status ON jobs (status);
CREATE INDEX IF NOT EXISTS idx_jobs_queue_status ON jobs (queue_id, status);
CREATE INDEX IF NOT EXISTS idx_jobs_priority ON jobs (priority);
CREATE INDEX IF NOT EXISTS idx_jobs_scheduled_time ON jobs (scheduled_time);
CREATE INDEX IF NOT EXISTS idx_jobs_next_retry ON jobs (next_retry_time);
CREATE INDEX IF NOT EXISTS idx_worker_heartbeats_worker_id ON worker_heartbeats (worker_id);
