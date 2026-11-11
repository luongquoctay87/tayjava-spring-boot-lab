package com.tayjava.demo.layered;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Thin controller sketch: delegates to a service (preferred direction vs fat controller).
 */
@RestController
@RequiredArgsConstructor
public class EnrollmentController {

  private final EnrollmentService enrollmentService;

  public record EnrollRequest(String studentEmail) {}

  @PostMapping("/api/demo/layered/courses/{courseId}/enrollments")
  public Enrollment enroll(@PathVariable long courseId, @RequestBody EnrollRequest request) {
    return enrollmentService.enroll(courseId, request.studentEmail());
  }
}
