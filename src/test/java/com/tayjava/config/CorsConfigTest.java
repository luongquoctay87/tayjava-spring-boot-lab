package com.tayjava.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

class CorsConfigTest extends AbstractPostgresIntegrationTest {

  @Autowired private CorsConfig corsConfig;

  @Test
  void corsConfigIsWebMvcConfigurer() {
    assertThat(corsConfig).isInstanceOf(WebMvcConfigurer.class);
  }
}
