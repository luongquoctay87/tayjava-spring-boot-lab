package com.tayjava.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

class CourseServiceContextTest extends AbstractPostgresIntegrationTest {

  @Autowired private ApplicationContext context;

  @Test
  void courseServiceIsOneSharedSingleton() {
    CourseService first = context.getBean(CourseService.class);
    CourseService second = context.getBean(CourseService.class);
    assertThat(first).isSameAs(second);

    first.create(
        new CourseReq(
            "Spring Boot from zero", "spring-boot-from-zero", ECourseLevel.BEGINNER, true));
    assertThat(second.list()).extracting(c -> c.slug()).contains("spring-boot-from-zero");
  }
}
