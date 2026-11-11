package com.tayjava.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.TestcontainersConfig;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfig.class)
class CourseRepositoryTest {

  @Autowired private CourseRepository courses;

  @Test
  void findsBySlug() {
    courses.save(new Course("Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true));

    assertThat(courses.existsBySlug("spring-boot")).isTrue();
    assertThat(courses.findBySlug("spring-boot"))
        .get()
        .extracting(Course::getTitle)
        .isEqualTo("Spring Boot");
  }
}
