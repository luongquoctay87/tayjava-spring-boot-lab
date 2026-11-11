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
| Branch | `lesson-27-jpa-relations` |
| Parent | `lesson-26-jpa` |
| What changed | JPA `Enrollment` with `@ManyToOne` Course (LAZY) and unique `(course_id, student_email)`. `EnrollmentController` + `EnrollReq` / `EnrollmentResp`. Reference `ddl/V2__create_enrollment.sql`. |


## Database

```bash
docker run --rm -d --name tayjava-pg \
  -e POSTGRES_USER=tayjava -e POSTGRES_PASSWORD=tayjava -e POSTGRES_DB=tayjava \
  -p 5432:5432 postgres:16-alpine
```

Optional manual DDL (Hibernate `ddl-auto=update` also creates the table): `psql` against the DB and apply `ddl/V1__create_course.sql`.


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
