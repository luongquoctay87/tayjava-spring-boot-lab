package com.tayjava.config;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
@ConditionalOnProperty(prefix = "tayjava.seed", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j(topic = "LocalCourseSeeder")
public class LocalCourseSeeder implements ApplicationRunner {

  private final CourseService courseService;

  @Override
  public void run(ApplicationArguments args) {
    courseService.create(
        new CourseReq(
            "Spring Boot Fundamentals",
            "spring-boot-fundamentals",
            ECourseLevel.BEGINNER,
            true));
    courseService.create(
        new CourseReq(
            "Spring Security in Practice",
            "spring-security-in-practice",
            ECourseLevel.INTERMEDIATE,
            false));
    log.info("Seeded {} demo courses", courseService.list().size());
  }
}
