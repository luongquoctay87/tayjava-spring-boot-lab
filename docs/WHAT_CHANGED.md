# lesson-16-rest-memory

- **Parent:** `lesson-14-runners`
- **What changed:** In-memory `entity.Course` + `CourseController` read/delete REST (`GET/DELETE /api/courses`).

## Smoke

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
curl http://localhost:8081/api/courses
```

Run tests: `./mvnw -q test`
