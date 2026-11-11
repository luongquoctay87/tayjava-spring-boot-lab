# Demo: JSON with Jackson (lesson 19)

**Kind:** `demo` (side branch — do **not** merge into the Course API project line)

| | |
|---|---|
| Branch | `demo/lesson-19-jackson` |
| Parent | `master` |
| Package | `com.tayjava.demo` |
| Entry | `com.tayjava.TayJavApplication` |

## What changed

- `ClockDemoController` — Jackson 3 Java Time + `@JsonFormat` (`GET /demo/clock`)
- `SnakeCourseId` — `@JsonProperty("course_id")` renaming pitfall
- Demo components live under `com.tayjava.demo` and are scanned by `TayJavApplication`

## Run / test

```bash
./mvnw -q test
./mvnw spring-boot:run
curl http://localhost:8080/demo/clock
```
