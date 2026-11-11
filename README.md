# tayjava-spring-boot-lab

Production-shaped companion lab for the TayJava **Spring Boot** series  
(Java **21** · Spring Boot **4.0.9** · package `com.tayjava.elearning`).

## Model

One continuous application. Each lesson with code is a **git branch** created from the previous lesson branch (delta only — not a disconnected copy).

| | |
|---|---|
| **Repo** | https://github.com/luongquoctay87/tayjava-spring-boot-lab |
| **Default branch** | `master` = lesson 05 skeleton |
| **License** | MIT |
| **Lesson map** | TayJava monorepo → `blogs/java/spring-boot/lab-samples-map.md` |
| **Code standard** | `ai-rules/tayjava-java-code.mdc` |

```text
master (lesson 05)
  └─ lesson-06-pom → … → lesson-50-capstone
```

## Requirements

- JDK 21+
- Maven Wrapper (`./mvnw`) — no global Maven required

## Quick start (`master` / lesson 05)

```bash
git clone https://github.com/luongquoctay87/tayjava-spring-boot-lab.git
cd tayjava-spring-boot-lab
git checkout master
./mvnw -q test
./mvnw spring-boot:run
```

Server: `http://localhost:8080`  
No HTTP API yet — Hello arrives on `lesson-08-hello`.

## Later lessons

```bash
git checkout lesson-XX-…   # see lab-samples-map.md
./mvnw -q test
./mvnw spring-boot:run
```

## Branch checklist (contributors / agents)

1. `git checkout <parent> && git checkout -b <branch>` — parent from the map.
2. English comments, README, and demo strings only.
3. `./mvnw -q test` green before `git push`.
4. No `HelloController` / `CorsConfig` on `lesson-32-lab` or `lesson-50-capstone`.
5. DB lessons: `ddl/` (26–30) or Flyway (31+) + apply steps in README.
6. Do **not** add disconnected `samples/` app trees.

## What changed on `master` (lesson 05)

- Skeleton: `pom.xml`, `ElearningApiApplication`, `application.yml`, context-load test.
- Feature packages (`course` / `enrollment`) appear in later branches.
