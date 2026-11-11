# Demo: Common beginner mistakes / anti-patterns (lesson 04)

**Kind:** `demo` (side branch)

| | |
|---|---|
| Branch | `demo/lesson-04-anti-patterns` |
| Parent | `master` |
| Package | `com.tayjava.demo.anti`, `com.tayjava.demo.layered` |

## WARNING — do not copy to production

This branch intentionally shows **anti-patterns** (god controller, field injection) next to a thin layered sketch. Use it to recognize mistakes only. Do **not** merge into the Course API project line or copy these patterns into production code.

## What changed

- `FatEnrollmentController` — validation + rules + store inside the controller
- `FieldInjectionAntiPattern` — `@Autowired` on a field (discouraged)
- `layered/*` — thin controller + service contrast sketch

## Run / test

```bash
./mvnw -q test
./mvnw spring-boot:run
```
