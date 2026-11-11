package com.tayjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.common.enums.EEnrollmentStatus;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.service.CourseService;
import com.tayjava.service.EnrollmentService;
import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TayJavApplicationTests extends AbstractPostgresIntegrationTest {

  @Autowired private CourseService courseService;
  @Autowired private EnrollmentService enrollmentService;

  @Test
  void enrollsOnceOnly() {
    Long courseId =
        courseService
            .create(
                new CourseReq(
                    "Spring Boot",
                    "spring-boot-it-" + System.nanoTime(),
                    ECourseLevel.BEGINNER,
                    true))
            .id();

    assertThat(enrollmentService.enroll(courseId, "alice@example.com").status())
        .isEqualTo(EEnrollmentStatus.ACTIVE);
    assertThatThrownBy(() -> enrollmentService.enroll(courseId, "alice@example.com"))
        .isInstanceOf(ErrorException.class)
        .hasMessageContaining("Already enrolled");
  }
}
