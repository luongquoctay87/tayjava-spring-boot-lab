# Capstone checklist — TayJava Spring Boot lab

Self-check against the continuous app on `lesson-50-capstone` (no `HelloController` / `CorsConfig`).

## Product

- [ ] Course CRUD under `/api/courses` with `Resp` / `ErrorException` codes
- [ ] Enrollment: enroll, get, list by email, cancel; 409 `ALREADY_ENROLLED` / `COURSE_HAS_ENROLLMENTS`
- [ ] Flyway `V1`/`V2`, `ddl-auto: validate`, `open-in-view: false`
- [ ] Layered packages: `controller` / `service`(+Impl) / `repository` / `entity` / `common` / `config`

## Production basics

- [ ] Actuator exposes only `health,info`
- [ ] Logging levels + MDC `requestId` (`RequestIdFilter`)
- [ ] Secrets via env / `.env` (never committed); `.env.example` present
- [ ] Fat JAR: `./mvnw -DskipTests package` → `java -jar …`
- [ ] Optional Docker image from `Dockerfile`
- [ ] Hikari `maximum-pool-size` set sensibly (default 10 here)

## Advanced pieces on this tip

- [ ] `@Scheduled` enrollment stats job
- [ ] `@Async` notification + AFTER_COMMIT event listeners
- [ ] `@Cacheable` / `@CacheEvict` on `CourseServiceImpl`
- [ ] `RestClient` `CouponClient` + `@RestClientTest`
- [ ] Virtual threads profile (`application-virtual.yml`)

## Tests

- [ ] `@WebMvcTest` for Course / Enrollment controllers
- [ ] `@DataJpaTest` + Testcontainers for repositories
- [ ] At least one `@SpringBootTest` flow (lab IT / enroll-once)

## Interview prompts

- Why slices (`@WebMvcTest` / `@DataJpaTest`) before full `@SpringBootTest`?
- How do `saveAndFlush` + unique constraints map to 409?
- When not to enable virtual threads on production?
