package com.tayjava.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.TestcontainersConfig;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.common.enums.EEnrollmentStatus;
import com.tayjava.entity.Course;
import com.tayjava.entity.Enrollment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfig.class)
class EnrollmentRepositoryCountTest {

  @Autowired private CourseRepository courses;
  @Autowired private EnrollmentRepository enrollments;

  @Test
  void countsByStatus() {
    Course course =
        courses.save(new Course("Spring Boot", "spring-boot-count", ECourseLevel.BEGINNER, true));
    Enrollment active = enrollments.save(new Enrollment(course, "alice@example.com"));
    Enrollment cancelled = enrollments.save(new Enrollment(course, "bob@example.com"));
    cancelled.cancel();
    enrollments.save(cancelled);

    assertThat(enrollments.countByStatus(EEnrollmentStatus.ACTIVE)).isEqualTo(1);
    assertThat(enrollments.countByStatus(EEnrollmentStatus.CANCELLED)).isEqualTo(1);
    assertThat(active.getStatus()).isEqualTo(EEnrollmentStatus.ACTIVE);
  }
}
