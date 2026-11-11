package com.tayjava.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.enums.ECourseLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseServiceContextTest {

  @Autowired private CourseService courseService;

  @Test
  void createAndListRoundTrip() {
    courseService.create(
        new CourseReq("Spring Boot", "spring-boot-ctx", ECourseLevel.BEGINNER, true));

    assertThat(courseService.list())
        .anyMatch(c -> "spring-boot-ctx".equals(c.slug()));
  }
}
