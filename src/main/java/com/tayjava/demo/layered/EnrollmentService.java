package com.tayjava.demo.layered;

import org.springframework.stereotype.Service;

/**
 * Contrast sketch: business rules live in a service (testable without HTTP).
 * Still a demo sketch — not the real Course API EnrollmentService.
 */
@Service
public class EnrollmentService {

    public Enrollment enroll(long courseId, String studentEmail) {
        return new Enrollment(1L, courseId, studentEmail, "ACTIVE");
    }
}
