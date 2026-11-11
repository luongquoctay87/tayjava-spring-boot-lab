# lesson-23-dto-entity

- **Parent:** `lesson-22-exception-handler`
- **What changed:** Controllers keep DTOs only (`CourseReq`, `CourseUpdateReq`, `CourseResp`). Domain `Course` stays in `entity/`; map manually via `CourseResp.from`. No JPA yet.

## Smoke

```bash
curl -i -X POST http://localhost:8080/api/courses \
  -H 'Content-Type: application/json' \
  -d '{"title":"","slug":"Bad Slug","level":"EXPERT","published":true}'
# expect HTTP 400 with $.code == VALIDATION_ERROR
```

Run tests: `./mvnw -q test`
