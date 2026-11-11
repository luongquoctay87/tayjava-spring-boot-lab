package com.tayjava.demo.anti;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ANTI-PATTERN: god controller with validation, rules, and storage inline.
 * Demo only — do not copy this into production or the Course API project line.
 */
@RestController
public class FatEnrollmentController {

    private final Map<Long, Enrollment> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public record EnrollRequest(String studentEmail) {}

    public record Enrollment(long id, long courseId, String studentEmail, String status) {}

    @PostMapping("/api/courses/{courseId}/enrollments")
    public Enrollment enroll(@PathVariable long courseId, @RequestBody EnrollRequest request) {
        // Validation, business rules, storage: all inside the controller (anti-pattern)
        if (request.studentEmail() == null || !request.studentEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid studentEmail");
        }
        boolean duplicated = store.values().stream()
                .anyMatch(e -> e.courseId() == courseId
                        && e.studentEmail().equals(request.studentEmail())
                        && "ACTIVE".equals(e.status()));
        if (duplicated) {
            throw new IllegalStateException("Already enrolled in this course");
        }
        Enrollment saved = new Enrollment(
                seq.getAndIncrement(), courseId, request.studentEmail(), "ACTIVE");
        store.put(saved.id(), saved);
        return saved;
    }
}
