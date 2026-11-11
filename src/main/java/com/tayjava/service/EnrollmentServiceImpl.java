package com.tayjava.service;

import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.enrollment.EnrollmentResp;
import com.tayjava.common.enums.EEnrollmentStatus;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
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
      throw new ErrorException(
          ErrorCode.COURSE_NOT_PUBLISHED, "Course is not published: " + courseId);
    }
    boolean alreadyEnrolled =
        enrollments.values().stream()
            .anyMatch(e -> e.courseId().equals(courseId) && e.studentEmail().equals(studentEmail));
    if (alreadyEnrolled) {
      throw new ErrorException(
          ErrorCode.ALREADY_ENROLLED, "Already enrolled in course " + courseId);
    }
    long id = sequence.incrementAndGet();
    EnrollmentResp enrollment =
        new EnrollmentResp(id, courseId, studentEmail, EEnrollmentStatus.ACTIVE);
    enrollments.put(id, enrollment);
    return enrollment;
  }
}
