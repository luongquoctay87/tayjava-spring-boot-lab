package com.tayjava.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tayjava.TestcontainersConfig;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.entity.Course;
import com.tayjava.entity.Enrollment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfig.class)
class EnrollmentRepositoryTest {

  @Autowired private CourseRepository courses;
  @Autowired private EnrollmentRepository enrollments;

  @Test
  void findsEnrollmentByCourseAndEmail() {
    Course course = courses.save(new Course("Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true));
    enrollments.save(new Enrollment(course, "alice@example.com"));

    assertThat(enrollments.existsByCourseIdAndStudentEmail(course.getId(), "alice@example.com"))
        .isTrue();
    assertThat(enrollments.existsByCourseIdAndStudentEmail(course.getId(), "bob@example.com"))
        .isFalse();
  }

  @Test
  void rejectsDuplicateEnrollment() {
    Course course = courses.save(new Course("Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true));
    enrollments.saveAndFlush(new Enrollment(course, "alice@example.com"));

    assertThatThrownBy(() -> enrollments.saveAndFlush(new Enrollment(course, "alice@example.com")))
        .isInstanceOf(DataIntegrityViolationException.class);
  }
}
