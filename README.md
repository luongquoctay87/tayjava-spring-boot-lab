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
| Branch | `lesson-24-api-design` |
| Parent | `lesson-23-dto-entity` |
| What changed | Locks the Course API contract: resources, HTTP statuses (201/204/400/404/409), and `ErrorCode` values (`VALIDATION_ERROR`, `COURSE_NOT_FOUND`, `SLUG_TAKEN`). Enrollment routes are specified for later JPA lessons. |

### Locked Course API

| Method | Path | Success | Error codes |
|--------|------|---------|-------------|
| `GET` | `/api/courses` | 200 `Resp<data[]>` | — |
| `GET` | `/api/courses/{id}` | 200 | `COURSE_NOT_FOUND` 404 |
| `POST` | `/api/courses` | 201 + `Location` | `VALIDATION_ERROR` 400, `SLUG_TAKEN` 409 |
| `PUT` | `/api/courses/{id}` | 200 | `VALIDATION_ERROR` 400, `COURSE_NOT_FOUND` 404 |
| `DELETE` | `/api/courses/{id}` | 204 | `COURSE_NOT_FOUND` 404 |

### Enrollment routes (implemented from lesson 27)

| Method | Path | Success | Error codes |
|--------|------|---------|-------------|
| `POST` | `/api/courses/{courseId}/enrollments` | 201 + `Location` | `COURSE_NOT_FOUND`, `COURSE_NOT_PUBLISHED`, `ALREADY_ENROLLED` |
| `GET` | `/api/enrollments/{id}` | 200 | `ENROLLMENT_NOT_FOUND` |
| `GET` | `/api/enrollments?studentEmail=` | 200 | — |
| `POST` | `/api/enrollments/{id}/cancel` | 200 | `ENROLLMENT_NOT_FOUND` |

## Run (current branch)

```bash
./mvnw -q test
./mvnw spring-boot:run
```

Default URL: `http://localhost:8080`

Smoke (expect 400):

```bash
curl -i -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"title":"","slug":"Bad Slug","level":"EXPERT","published":true}'
```
