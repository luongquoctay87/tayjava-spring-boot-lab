package com.tayjava.common.dto.enrollment;

import com.tayjava.common.enums.EEnrollmentStatus;
import com.tayjava.entity.Enrollment;

public record EnrollmentResp(
    Long id, Long courseId, String studentEmail, EEnrollmentStatus status) {

  public static EnrollmentResp from(Enrollment enrollment) {
    return new EnrollmentResp(
        enrollment.getId(),
        enrollment.getCourse().getId(),
        enrollment.getStudentEmail(),
        enrollment.getStatus());
  }
}
