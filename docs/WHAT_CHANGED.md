# lesson-48-virtual-threads

- **Parent:** `lesson-47-restclient`
- **What changed:** Add profile `application-virtual.yml` (`spring.threads.virtual.enabled=true`) and switch `enrollmentExecutor` to `newVirtualThreadPerTaskExecutor()`.

Run tests: `./mvnw -q test`
