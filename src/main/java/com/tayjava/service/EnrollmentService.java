package com.tayjava.service;

import com.tayjava.common.dto.enrollment.EnrollmentResp;
import java.util.List;

public interface EnrollmentService {

  EnrollmentResp enroll(Long courseId, String studentEmail);

  EnrollmentResp get(Long id);

  List<EnrollmentResp> listByStudent(String studentEmail);

  EnrollmentResp cancel(Long id);
}
