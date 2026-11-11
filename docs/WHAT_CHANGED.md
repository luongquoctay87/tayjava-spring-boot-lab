# lesson-17-input

- **Parent:** `lesson-16-rest-memory`
- **What changed:** `@RequestBody` / `@PathVariable` for `POST` create and `PUT` update on `/api/courses`.

## Smoke

```bash
curl -X POST http://localhost:8080/api/courses \
  -H 'Content-Type: application/json' \
  -d '{"title":"Spring Boot","slug":"spring-boot","level":"BEGINNER","published":true}'
```

Run tests: `./mvnw -q test`
