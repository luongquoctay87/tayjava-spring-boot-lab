# lesson-08-hello

- **Parent:** `lesson-07-config`
- **What changed:** Temporary `HelloController` at `GET /api/hello` reading `tayjava.hello.message`. Remove before lessons 32/50.

## Smoke

```bash
./mvnw spring-boot:run
curl http://localhost:8080/api/hello
```

Run tests: `./mvnw -q test`
