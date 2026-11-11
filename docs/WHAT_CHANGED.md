# lesson-22-exception-handler

- **Parent:** `lesson-21-validation`
- **What changed:** `ErrorException` + `ErrorCode` + `GlobalExceptionHandling` → `Resp` envelope. Controllers return `ResponseEntity<Resp<T>>` via `ApiResp`. Course/Enrollment services throw `ErrorException`. Seed runners stay under `config/`.

## Smoke

```bash
curl -i -X POST http://localhost:8080/api/courses \
  -H 'Content-Type: application/json' \
  -d '{"title":"","slug":"Bad Slug","level":"EXPERT","published":true}'
# expect HTTP 400 with $.code == VALIDATION_ERROR
```

Run tests: `./mvnw -q test`
