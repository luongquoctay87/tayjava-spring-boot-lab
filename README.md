# Demo: IoC container and bean lifecycle (lesson 09)

**Kind:** `demo` (side branch — do **not** merge into the Course API project line)

| | |
|---|---|
| Branch | `demo/lesson-09-ioc-lifecycle` |
| Parent | `master` |
| Package | `com.tayjava.demo` (test only, outside app scan) |

## What changed

Adds `BeanLifecycleDemoTest`: plain `AnnotationConfigApplicationContext` proves constructor → `@PostConstruct` → ready → `@PreDestroy`, singleton vs prototype (no `@PreDestroy` for prototype).

## Run / test

```bash
./mvnw -q test
./mvnw -q -Dtest=BeanLifecycleDemoTest test
```
