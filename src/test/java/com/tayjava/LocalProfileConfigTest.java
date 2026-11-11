package com.tayjava;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("local")
@TestPropertySource(properties = "DB_PASSWORD=tayjava")
class LocalProfileConfigTest extends AbstractPostgresIntegrationTest {

  @Value("${server.port}")
  int port;

  @Value("${tayjava.seed.enabled}")
  boolean seedEnabled;

  @Value("${spring.application.name}")
  String appName;

  @Test
  void localProfileOverridesBaseValues() {
    assertThat(port).isEqualTo(8081);
    assertThat(seedEnabled).isTrue();
    assertThat(appName).isEqualTo("tayjava-spring-boot-lab");
  }
}
