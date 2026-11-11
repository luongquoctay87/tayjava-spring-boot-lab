# Demo: Framework vs Boot (lesson 03)

**Kind:** `demo` (side branch — do **not** merge into the Course API project line)

| | |
|---|---|
| Branch | `demo/lesson-03-framework-vs-boot` |
| Parent | `master` |
| Package | `com.tayjava.demo.catalog` |

## What changed

Standalone catalog demo that shows Framework stereotypes (`@Service`, `@Repository`, `@RestController`) running inside a Boot host. `ElearningApiApplication` scans `com.tayjava.demo.catalog` on this side branch only.

## Run / test

```bash
./mvnw -q test
./mvnw spring-boot:run
curl http://localhost:8080/api/courses
```
