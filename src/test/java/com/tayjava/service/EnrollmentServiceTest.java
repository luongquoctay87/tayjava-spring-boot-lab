package com.tayjava.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.enrollment.EnrollmentResp;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.common.enums.EEnrollmentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

  @Mock private CourseService courseService;

  @InjectMocks private EnrollmentServiceImpl enrollmentService;

  @Test
  void enrollPublishedCourse() {
    when(courseService.get(1L))
        .thenReturn(new CourseResp(1L, "Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true));

    EnrollmentResp enrollment = enrollmentService.enroll(1L, "alice@example.com");

    assertThat(enrollment.courseId()).isEqualTo(1L);
    assertThat(enrollment.studentEmail()).isEqualTo("alice@example.com");
    assertThat(enrollment.status()).isEqualTo(EEnrollmentStatus.ACTIVE);
  }

  @Test
  void enrollUnpublishedCourseFails() {
    when(courseService.get(1L))
        .thenReturn(new CourseResp(1L, "Draft", "draft", ECourseLevel.BEGINNER, false));

    assertThatThrownBy(() -> enrollmentService.enroll(1L, "alice@example.com"))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("not published");
  }
}
