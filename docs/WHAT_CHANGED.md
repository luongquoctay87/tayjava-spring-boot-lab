# lesson-38-logging

- **Parent:** `lesson-37-actuator`
- **What changed:** Add `RequestIdFilter` (MDC `requestId` + `X-Request-Id`), console pattern with `%X{requestId}`, quieter Hibernate SQL, and lean `application-prod.yml` logging.

Run tests: `./mvnw -q test`
