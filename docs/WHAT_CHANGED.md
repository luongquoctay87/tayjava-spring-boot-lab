# lesson-14-runners

- **Parent:** `lesson-13-profile-conditional`
- **What changed:** Ordered runners in `config/`: `LocalCourseSeeder` (`ApplicationRunner` `@Order(1)`, skip if store non-empty) + `SeedReportRunner` (`CommandLineRunner` `@Order(2)`).

Run tests: `./mvnw -q test`
