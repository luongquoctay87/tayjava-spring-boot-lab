package com.tayjava.controller;

import com.tayjava.common.dto.ApiResp;
import com.tayjava.common.dto.Resp;
import com.tayjava.common.dto.enrollment.EnrollReq;
import com.tayjava.common.dto.enrollment.EnrollmentResp;
import com.tayjava.service.EnrollmentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {

  private final EnrollmentService enrollmentService;

  @PostMapping("/api/courses/{courseId}/enrollments")
  public ResponseEntity<Resp<EnrollmentResp>> enroll(
      @PathVariable Long courseId, @Valid @RequestBody EnrollReq req) {
    EnrollmentResp created = enrollmentService.enroll(courseId, req.studentEmail());
    return ApiResp.created(URI.create("/api/enrollments/" + created.id()), created);
  }

  @GetMapping("/api/enrollments/{id}")
  public ResponseEntity<Resp<EnrollmentResp>> get(@PathVariable Long id) {
    return ApiResp.ok(enrollmentService.get(id));
  }

  @GetMapping("/api/enrollments")
  public ResponseEntity<Resp<List<EnrollmentResp>>> listByStudent(
      @RequestParam String studentEmail) {
    return ApiResp.ok(enrollmentService.listByStudent(studentEmail));
  }

  @PostMapping("/api/enrollments/{id}/cancel")
  public ResponseEntity<Resp<EnrollmentResp>> cancel(@PathVariable Long id) {
    return ApiResp.ok(enrollmentService.cancel(id));
  }
}
