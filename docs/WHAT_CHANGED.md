# lesson-06-pom

- **Parent:** `master` (lesson 05)
- **What changed:** Clarified Maven layout and Spring Boot starters in `pom.xml` comments; documented the continuous lab tree for readers.

## Maven tree (standard)

```text
src/main/java          application sources (com.tayjava)
src/main/resources     application.yml and static config
src/test/java          tests mirroring main packages
pom.xml                coordinates, parent BOM, starters, plugins
```

## Starters on the classpath

| Dependency | Role |
|---|---|
| `spring-boot-starter-webmvc` | Spring MVC + embedded Tomcat + Jackson |
| `spring-boot-starter-validation` | Bean Validation (used from later lessons) |
| `spring-boot-starter-webmvc-test` (test) | `@SpringBootTest`, MockMvc slices |

Run: `./mvnw -q test` then `./mvnw spring-boot:run` (port **8080**).
