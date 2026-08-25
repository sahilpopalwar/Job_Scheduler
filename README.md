
# Job Scheduler

A combined Spring Boot + React job scheduling dashboard built as a single application. The backend serves the React frontend from the same origin, so there is no separate frontend dev server required for deployment.

## Overview

This project provides:
- enterprise-style dashboard UI for job operations
- authentication and authorization using JWT
- queue, job, worker, and DLQ views
- Spring Boot backend serving the frontend static bundle
- in-memory H2 database for local development

## Tech Stack

- Java 21
- Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA
- H2 Database (local dev)
- React + Vite
- Recharts

## Project Structure

```text
jobsceduler/
├── backend/
│   ├── src/main/java
│   ├── src/main/resources
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   ├── src/
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
├── pom.xml
├── docker-compose.yml
├── README.md
└── .gitignore
```

## Run the project

### 1) Build the combined app

```bash
mvn -q -pl backend package
```

### 2) Start the app in dev mode

On Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE='dev'
java -jar .\backend\target\jobscheduler-backend-0.0.1-SNAPSHOT.jar
```

On macOS/Linux:

```bash
SPRING_PROFILES_ACTIVE=dev java -jar backend/target/jobscheduler-backend-0.0.1-SNAPSHOT.jar
```

The application will run on:

```text
http://localhost:8080
```

## Default login

Use the seeded developer account:

```text
Username: admin
Password: admin123
```

## Routes

- `/` -> dashboard landing page
- `/login` -> login page
- `/dashboard` -> overview dashboard
- `/queues` -> queue monitoring
- `/jobs` -> job execution views
- `/workers` -> worker fleet status
- `/dlq` -> dead-letter queue monitoring

## Notes

- The backend is configured to use H2 in development so the app runs locally without external PostgreSQL setup.
- Frontend assets are built by the Maven pipeline and copied into the backend static resources before packaging.
- The project is intended to be run as a single combined app rather than separate frontend and backend servers.

## Useful commands

```bash
# Build only the backend package
mvn -q -pl backend package

# Run the app in dev mode
SPRING_PROFILES_ACTIVE=dev java -jar backend/target/jobscheduler-backend-0.0.1-SNAPSHOT.jar

# Health check
curl http://localhost:8080/actuator/health
```
