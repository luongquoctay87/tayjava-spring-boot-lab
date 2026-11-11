# lesson-35-datajpatest

- **Parent:** `lesson-34-mockmvc`
- **What changed:** Add `@DataJpaTest` for `CourseRepository` / `EnrollmentRepository` on Testcontainers PostgreSQL (`TestcontainersConfig` + `@ServiceConnection`), plus `spring-boot-starter-data-jpa-test`. Unique constraint covered with `saveAndFlush`.

Run tests: `./mvnw -q test`
