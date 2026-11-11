# lesson-07-config

- **Parent:** `lesson-06-pom`
- **What changed:** Added `application-local.yml` (port 8081, DEBUG logging) and `tayjava.hello.message`. Profile `local` verified by test.

## Smoke

```bash
./mvnw spring-boot:run
# default profile → http://localhost:8080

./mvnw spring-boot:run -Dspring-boot.run.profiles=local
# local profile → http://localhost:8081
```

Run tests: `./mvnw -q test`
