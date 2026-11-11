# TayJava Spring Boot Lab

Sample Spring Boot application that grows with the **TayJava Spring Boot** article series.

Readers build one continuous app while learning Spring Boot: from a bare project, through REST APIs for **Course** and **Enrollment**, to JPA, PostgreSQL, Flyway, tests, and a small production checklist. Each lesson that changes the code has a matching **git branch** so you can check out the state that matches the article.

This repository is a companion for learning — not the TayJava production backend.

## What you will build

- Layered monolith under package `com.tayjava` (`controller` / `service` / `entity` / `common`)
- REST API around `Course` and `Enrollment` (in-memory first, then JPA + PostgreSQL)
- Validation, centralized error handling (`ErrorException` / `Resp`), Flyway migrations, and automated tests on later branches

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
| Branch | `lesson-31-flyway` |
| Parent | `lesson-30-transactional` |
| What changed | Flyway owns schema via `db/migration/V1__create_course.sql` and `V2__create_enrollment.sql`. `ddl-auto: validate`, `open-in-view: false`. Reference `ddl/` remains for earlier lessons; Flyway is the source of truth here. |


## Database

```bash
docker run --rm -d --name tayjava-pg \
  -e POSTGRES_USER=tayjava -e POSTGRES_PASSWORD=tayjava -e POSTGRES_DB=tayjava \
  -p 5432:5432 postgres:16-alpine
```

Flyway runs migrations on startup. Use a fresh database (or drop Hibernate-created tables) so `V1`/`V2` apply cleanly. `ddl/` copies remain as reference only.


## Run (current branch)

```bash
./mvnw -q test
./mvnw spring-boot:run
```

Default URL: `http://localhost:8080`

Smoke:

```bash
curl -i -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"title":"Spring Boot from zero","slug":"spring-boot-from-zero","level":"BEGINNER","published":true}'
```
