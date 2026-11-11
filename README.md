# Demo: HTTP request lifecycle (lesson 15)

**Kind:** `demo` (side branch — do **not** merge into lab/capstone)

| | |
|---|---|
| Branch | `demo/lesson-15-request-lifecycle` |
| Parent | `master` |
| Package | `com.tayjava.demo` |
| Entry | `com.tayjava.TayJavApplication` |

## What changed

Temporary filter + interceptor + hello endpoint under `com.tayjava.demo` (scanned by `TayJavApplication`):

- `RequestTimingFilter` — servlet filter (before/after DispatcherServlet)
- `HandlerLoggingInterceptor` + `DemoWebConfig`
- `DemoHelloController` — `GET /api/hello`

Watch the log order: filter before → preHandle → postHandle → afterCompletion → filter after.

## Run / test

```bash
./mvnw -q test
./mvnw spring-boot:run
curl http://localhost:8080/api/hello
```
