package com.tayjava.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootTest
class CorsConfigTest {

  @Autowired private CorsConfig corsConfig;

  @Test
  void corsConfigIsWebMvcConfigurer() {
    assertThat(corsConfig).isInstanceOf(WebMvcConfigurer.class);
  }
}
