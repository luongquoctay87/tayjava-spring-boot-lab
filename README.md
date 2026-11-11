# TayJava Spring Boot Lab

Sample Spring Boot application that grows with the **TayJava Spring Boot** article series.

Readers build one continuous app while learning Spring Boot: from a bare project, through REST APIs for **Course** and **Enrollment**, to JPA, PostgreSQL, Flyway, tests, and a small production checklist. Each lesson that changes the code has a matching **git branch** so you can check out the state that matches the article.

This repository is a companion for learning — not the TayJava production backend.

## What you will build

- Layered monolith under package `com.tayjava` (`controller` / `service` / `entity` / `common`)
- REST API around `Course` and `Enrollment` (JPA + PostgreSQL + Flyway)
- Validation, centralized error handling (`ErrorException` / `Resp`), and automated tests

## Tech stack

| Area | Choice |
|------|--------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.9 |
| Web | Spring MVC (`spring-boot-starter-webmvc`) |
| Validation | Bean Validation (`spring-boot-starter-validation`) |
| Build | Maven 3.9+ via Maven Wrapper (`./mvnw`) |
| Persistence (later lessons) | Spring Data JPA, PostgreSQL, Flyway |
| Tests | JUnit 5, Spring Boot test slices / Testcontainers (later lessons) |

## Project coordinates

| | |
|---|---|
| Group / artifact | `com.tayjava` / `tayjava-spring-boot-lab` |
| Main class | `com.tayjava.TayJavApplication` |
| Default branch | `master` — lesson 05 skeleton |
| License | MIT |

## This branch

| | |
|---|---|
| Branch | `lesson-50-capstone` |
| Parent | `lesson-48-virtual-threads` |
| What changed | Capstone self-check on the continuous app — `docs/CAPSTONE_CHECKLIST.md`; no `HelloController` / `CorsConfig`; Hikari pool size set. |


## Database

```bash
docker run --rm -d --name tayjava-pg \
  -e POSTGRES_USER=tayjava -e POSTGRES_PASSWORD=tayjava -e POSTGRES_DB=tayjava \
  -p 5432:5432 postgres:16-alpine
```

Flyway runs migrations on startup. Use a fresh database (or drop Hibernate-created tables) so `V1`/`V2` apply cleanly. `ddl/` copies remain as reference only.


## Run (current branch)

```bash
cp .env.example .env   # fill DB_PASSWORD for profile local/prod; never commit .env
./mvnw -q test
./mvnw spring-boot:run
# or: SPRING_PROFILES_ACTIVE=local DB_PASSWORD=tayjava ./mvnw spring-boot:run
```

Default URL: `http://localhost:8080` (profile `local` uses port `8081`)


## Docker (minimal image from fat JAR)

```bash
./mvnw -DskipTests package
docker build -t tayjava-spring-boot-lab:local .
docker run --rm -p 8080:8080 \
  -e DB_URL='jdbc:postgresql://host.docker.internal:5432/tayjava' \
  -e DB_USERNAME=tayjava \
  -e DB_PASSWORD=tayjava \
  tayjava-spring-boot-lab:local
# curl -s http://localhost:8080/actuator/health
```

## Package fat JAR (`java -jar`)

```bash
./mvnw -DskipTests package
# artifact: target/tayjava-spring-boot-lab-0.0.1-SNAPSHOT.jar
# *.jar.original is the thin jar before spring-boot-maven-plugin repackage

export DB_URL='jdbc:postgresql://localhost:5432/tayjava'
export DB_USERNAME=tayjava
export DB_PASSWORD=tayjava
export SPRING_PROFILES_ACTIVE=local

java -jar target/tayjava-spring-boot-lab-0.0.1-SNAPSHOT.jar
# optional: --server.port=8088

# Docker prep (layers):
# java -Djarmode=tools -jar target/tayjava-spring-boot-lab-0.0.1-SNAPSHOT.jar extract --layers --launcher
```

Smoke (expect 201 then 409 `SLUG_TAKEN`):

```bash
curl -i -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"title":"Spring Boot from zero","slug":"spring-boot-0","level":"BEGINNER","published":true}'

curl -i -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"title":"Spring Boot from zero","slug":"spring-boot-0","level":"BEGINNER","published":true}'
```
