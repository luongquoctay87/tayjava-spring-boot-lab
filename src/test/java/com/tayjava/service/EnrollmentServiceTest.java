package com.tayjava.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.enrollment.EnrollmentResp;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.common.enums.EEnrollmentStatus;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EnrollmentServiceTest extends AbstractPostgresIntegrationTest {

  @Autowired private CourseService courseService;
  @Autowired private EnrollmentService enrollmentService;

  @Test
  void enrollsIntoPublishedCourse() {
    CourseResp course =
        courseService.create(
            new CourseReq(
                "Spring Boot from zero",
                "enroll-published-" + System.nanoTime(),
                ECourseLevel.BEGINNER,
                true));

    EnrollmentResp enrollment = enrollmentService.enroll(course.id(), "alice@example.com");

    assertThat(enrollment.status()).isEqualTo(EEnrollmentStatus.ACTIVE);
    assertThat(enrollment.courseId()).isEqualTo(course.id());
  }

  @Test
  void rejectsUnpublishedCourse() {
    CourseResp draft =
        courseService.create(
            new CourseReq(
                "Draft", "enroll-draft-" + System.nanoTime(), ECourseLevel.BEGINNER, false));

    assertThatThrownBy(() -> enrollmentService.enroll(draft.id(), "alice@example.com"))
        .isInstanceOf(ErrorException.class)
        .extracting(ex -> ((ErrorException) ex).getErrorCode())
        .isEqualTo(ErrorCode.COURSE_NOT_PUBLISHED);
  }

  @Test
  void rejectsDuplicateEnrollment() {
    CourseResp course =
        courseService.create(
            new CourseReq(
                "Unique enroll",
                "enroll-dup-" + System.nanoTime(),
                ECourseLevel.BEGINNER,
                true));
    enrollmentService.enroll(course.id(), "bob@example.com");

    assertThatThrownBy(() -> enrollmentService.enroll(course.id(), "bob@example.com"))
        .isInstanceOf(ErrorException.class)
        .extracting(ex -> ((ErrorException) ex).getErrorCode())
        .isEqualTo(ErrorCode.ALREADY_ENROLLED);
  }
}
