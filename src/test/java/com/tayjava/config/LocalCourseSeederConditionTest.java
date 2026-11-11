package com.tayjava.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class LocalCourseSeederConditionTest {

  @Autowired private ApplicationContext context;

  @Test
  void seederAbsentOnDefaultProfile() {
    assertThat(context.getBeanNamesForType(LocalCourseSeeder.class)).isEmpty();
  }
}
