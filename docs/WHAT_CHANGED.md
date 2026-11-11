# lesson-13-profile-conditional

- **Parent:** `lesson-11-constructor-di`
- **What changed:** `LocalCourseSeeder` in `config/` with `@Profile("local")` + `@ConditionalOnProperty(tayjava.seed.enabled=true)`.

## Smoke

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
# expect seed log for two demo courses
```

Run tests: `./mvnw -q test`
