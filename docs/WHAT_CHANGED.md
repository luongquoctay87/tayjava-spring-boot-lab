# lesson-21-validation

- **Parent:** `lesson-20-cors`
- **What changed:** Bean Validation on `CourseReq` / `CourseUpdateReq` with `@Valid` on controller methods (400 on constraint violations). Central `Resp` error envelope arrives in lesson 22.

## Smoke

```bash
curl -i -X POST http://localhost:8080/api/courses \
  -H 'Content-Type: application/json' \
  -d '{"title":"","slug":"Bad Slug","level":"BEGINNER","published":true}'
# expect HTTP 400
```

Run tests: `./mvnw -q test`
