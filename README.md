# Demo: JSON with Jackson (lesson 19)

**Kind:** `demo` (side branch — do **not** merge into the Course API project line)

| | |
|---|---|
| Branch | `demo/lesson-19-jackson` |
| Parent | `master` |
| Package | `com.tayjava.demo` |

## What changed

- `ClockDemoController` — Jackson 3 Java Time + `@JsonFormat` (`GET /demo/clock`)
- `SnakeCourseId` — `@JsonProperty("course_id")` renaming pitfall
- Temporary `@Import(ClockDemoController.class)` on `ElearningApiApplication`

## Run / test

```bash
./mvnw -q test
./mvnw spring-boot:run
curl http://localhost:8080/demo/clock
```
