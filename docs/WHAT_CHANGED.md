# lesson-26-jpa

- **Parent:** `lesson-24-api-design`
- **What changed:** JPA `Course` + `CourseRepository`; service persists with `@Transactional`. PostgreSQL + `ddl-auto: update`, `open-in-view: false`. Reference `ddl/V1__create_course.sql`. Testcontainers for `@SpringBootTest`.

## Database

```bash
docker run --rm -d --name tayjava-pg \
  -e POSTGRES_USER=tayjava -e POSTGRES_PASSWORD=tayjava -e POSTGRES_DB=tayjava \
  -p 5432:5432 postgres:16-alpine
```

Optional: `psql` + `ddl/V1__create_course.sql`.

Run tests: `./mvnw -q test`
