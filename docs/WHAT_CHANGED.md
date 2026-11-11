# lesson-34-mockmvc

- **Parent:** `lesson-32-lab`
- **What changed:** Focus `@WebMvcTest` / MockMvc for `CourseController` and `EnrollmentController`: `@MockitoBean` services, assert 201/`Location`, validation `VALIDATION_ERROR`, and `ErrorException` → `Resp` (`ALREADY_ENROLLED`) without a database. No production code change.

Run tests: `./mvnw -q test`
