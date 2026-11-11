# lesson-20-cors

- **Parent:** `lesson-18-response-entity`
- **What changed:** Optional `CorsConfig` for `/api/**` with `elearning.cors.allowed-origins` (default `http://localhost:5173`). Remove before lessons 32/50.

## Smoke

```bash
./mvnw spring-boot:run
curl -i -X OPTIONS http://localhost:8080/api/courses \
  -H 'Origin: http://localhost:5173' \
  -H 'Access-Control-Request-Method: GET'
```

Run tests: `./mvnw -q test`
