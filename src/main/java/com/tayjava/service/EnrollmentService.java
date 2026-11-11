package com.tayjava.service;

import com.tayjava.common.dto.enrollment.EnrollmentResp;

public interface EnrollmentService {

  EnrollmentResp enroll(Long courseId, String studentEmail);
}
