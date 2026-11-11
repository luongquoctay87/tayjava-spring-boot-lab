# Demo: @Configuration and @Bean (lesson 12)

**Kind:** `demo` (side branch — do **not** merge into the Course API project line)

| | |
|---|---|
| Branch | `demo/lesson-12-config-bean` |
| Parent | `master` |
| Package | `com.tayjava.demo` (test only) |

## What changed

Standalone Framework-container tests:

- `ClockBeanDemoTest` — declare `Clock` with `@Bean`
- `ConfigurationProxyDemoTest` — `proxyBeanMethods` vs method parameters
- `DuplicateBeanDemoTest` — `@Primary` / `@Qualifier` for two `RestClient` beans

## Run / test

```bash
./mvnw -q test
```
