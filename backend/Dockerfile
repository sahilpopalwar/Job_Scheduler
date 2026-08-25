# Multi-stage build: build with Maven then run with JDK
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
RUN mvn -DskipTests package -q

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /workspace/target/jobscheduler-0.0.1-SNAPSHOT.jar ./jobscheduler.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/jobscheduler.jar"]
