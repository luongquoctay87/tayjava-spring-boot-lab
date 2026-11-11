package com.tayjava.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class LocalCourseSeederPresentTest {

  @Autowired private ApplicationContext context;

  @Test
  void seederPresentOnLocalProfileWithSeedEnabled() {
    assertThat(context.getBean(LocalCourseSeeder.class)).isNotNull();
  }
}
