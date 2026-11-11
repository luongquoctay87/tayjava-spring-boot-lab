# lesson-24-api-design

- **Parent:** `lesson-23-dto-entity`
- **What changed:** Locks Course API resources, statuses (201/204/400/404/409), and `ErrorCode` values. Enrollment routes documented for later JPA lessons; no production code change required on this branch.

## Smoke

```bash
curl -i -X POST http://localhost:8080/api/courses \
  -H 'Content-Type: application/json' \
  -d '{"title":"","slug":"Bad Slug","level":"EXPERT","published":true}'
# expect HTTP 400 with $.code == VALIDATION_ERROR
```

Run tests: `./mvnw -q test`
