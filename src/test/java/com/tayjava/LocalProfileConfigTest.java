package com.tayjava;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class LocalProfileConfigTest {

  @Autowired private Environment environment;

  @Value("${tayjava.hello.message}")
  private String helloMessage;

  @Value("${server.port}")
  private int serverPort;

  @Test
  void localProfileOverridesPortAndHelloMessage() {
    assertThat(environment.getActiveProfiles()).contains("local");
    assertThat(serverPort).isEqualTo(8081);
    assertThat(helloMessage).contains("local profile");
  }
}
