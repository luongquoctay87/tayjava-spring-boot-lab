# Demo: Spring AOP / Aspect (lesson 42)

**Kind:** `demo` (side branch — do **not** merge into lab/capstone)

| | |
|---|---|
| Branch | `demo/lesson-42-aop` |
| Parent | `master` |
| Package | `com.tayjava.demo` |

## What changed

Minimal Aspect/AOP demo imported on `ElearningApiApplication`:

- `spring-boot-starter-aspectj` — Boot 4 AOP starter (proxy-based Spring AOP)
- `TimingAspect` — `@Around` advice that times `DemoEnrollmentService` methods
- `DemoEnrollmentService` — `enroll` (proxy path) vs `enrollViaSelfInvocation` (self-call skips aspect on the nested method)

Same idea as `@Transactional`: advice runs when the call goes through the Spring proxy, not on `this.method(...)`.

## Run / test

```bash
./mvnw -q test
./mvnw spring-boot:run
```

Watch the log for `DemoEnrollmentService.enroll(..)` timing when you inject and call the service from another bean.
