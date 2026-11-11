package com.tayjava.service;

import com.tayjava.common.dto.enrollment.EnrollmentResp;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.entity.Course;
import com.tayjava.event.StudentEnrolledEvent;
import com.tayjava.entity.Enrollment;
import com.tayjava.repository.EnrollmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service transaction boundary (lesson 30): read methods use {@code readOnly=true}; writes roll
 * back on unchecked {@link com.tayjava.common.exception.ErrorException}.
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "EnrollmentService")
public class EnrollmentServiceImpl implements EnrollmentService {

  private final EnrollmentRepository enrollments;
  private final CourseService courseService;
  private final ApplicationEventPublisher events;

  @Override
  @Transactional
  public EnrollmentResp enroll(Long courseId, String studentEmail) {
    log.info("Enrolling student={} into courseId={}", studentEmail, courseId);
    Course course = courseService.load(courseId);
    if (!course.isPublished()) {
      throw new ErrorException(
          ErrorCode.COURSE_NOT_PUBLISHED, "Course is not published: " + courseId);
    }
    if (enrollments.existsByCourseIdAndStudentEmail(courseId, studentEmail)) {
      throw new ErrorException(
          ErrorCode.ALREADY_ENROLLED, "Already enrolled in course " + courseId);
    }
    try {
      Enrollment saved = enrollments.saveAndFlush(new Enrollment(course, studentEmail));
      events.publishEvent(new StudentEnrolledEvent(saved.getId(), courseId, studentEmail));
      log.info("Enrolled student={} courseId={}", studentEmail, courseId);
      return EnrollmentResp.from(saved);
    } catch (DataIntegrityViolationException ex) {
      throw new ErrorException(
          ErrorCode.ALREADY_ENROLLED, "Already enrolled in course " + courseId);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public EnrollmentResp get(Long id) {
    return EnrollmentResp.from(load(id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<EnrollmentResp> listByStudent(String studentEmail) {
    return enrollments.findByStudentEmailOrderByEnrolledAtDesc(studentEmail).stream()
        .map(EnrollmentResp::from)
        .toList();
  }

  @Override
  @Transactional
  public EnrollmentResp cancel(Long id) {
    Enrollment enrollment = load(id);
    enrollment.cancel();
    return EnrollmentResp.from(enrollment);
  }

  private Enrollment load(Long id) {
    return enrollments
        .findById(id)
        .orElseThrow(
            () ->
                new ErrorException(
                    ErrorCode.ENROLLMENT_NOT_FOUND, "Enrollment not found: " + id));
  }
}
