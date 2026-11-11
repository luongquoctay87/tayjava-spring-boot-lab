package com.tayjava.service;

import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.enrollment.EnrollmentResp;
import com.tayjava.common.enums.EEnrollmentStatus;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

  private final CourseService courseService;
  private final Map<Long, EnrollmentResp> enrollments = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong();

  @Override
  public EnrollmentResp enroll(Long courseId, String studentEmail) {
    CourseResp course = courseService.get(courseId);
    if (!course.published()) {
      throw new IllegalStateException("Course is not published: " + courseId);
    }
    long id = sequence.incrementAndGet();
    EnrollmentResp enrollment =
        new EnrollmentResp(id, courseId, studentEmail, EEnrollmentStatus.ACTIVE);
    enrollments.put(id, enrollment);
    return enrollment;
  }
}
