# Demo: Spring Data JPA intro (lesson 25)

**Kind:** `demo` (side branch — do **not** merge into the Course API project line)

| | |
|---|---|
| Branch | `demo/lesson-25-spring-data-jpa-intro` |
| Parent | `master` |
| Package | `com.tayjava.demo` |
| Entry | `com.tayjava.TayJavApplication` |
| DB | Embedded H2 (self-contained; lesson 26 moves the Course API to PostgreSQL) |

## What changed

Minimal runnable preview of Spring Data JPA concepts from the article:

- `DemoCourse` — `@Entity` with `title` + unique `slug` (Lombok getters)
- `DemoCourseRepository` — `JpaRepository` plus derived queries `existsBySlug` / `findBySlug`
- `DemoJpaConfig` — `@EntityScan` + `@EnableJpaRepositories` for the demo package
- H2 in-memory datasource so the demo runs without PostgreSQL

## Run / test

```bash
./mvnw -q test
./mvnw spring-boot:run
```

Default URL: `http://localhost:8080` (no Course API endpoints on this side branch — persistence is exercised by the demo test).
