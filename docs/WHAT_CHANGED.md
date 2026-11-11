# lesson-46-events

- **Parent:** `lesson-45-cache`
- **What changed:** Publish `StudentEnrolledEvent` from enroll; audit `@EventListener` plus `@TransactionalEventListener(AFTER_COMMIT)` mail listener (async notification).

Run tests: `./mvnw -q test`
