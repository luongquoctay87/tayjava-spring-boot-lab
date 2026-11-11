package com.tayjava.config;

import com.tayjava.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
@Order(2)
@RequiredArgsConstructor
@Slf4j(topic = "SeedReportRunner")
public class SeedReportRunner implements CommandLineRunner {

  private final CourseService courseService;

  @Override
  public void run(String... args) {
    log.info(
        "Courses available at startup: {}",
        courseService.list(Pageable.ofSize(1)).getTotalElements());
  }
}
