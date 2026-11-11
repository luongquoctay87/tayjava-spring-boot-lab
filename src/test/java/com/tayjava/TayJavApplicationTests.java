package com.tayjava;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

class TayJavApplicationTests extends AbstractPostgresIntegrationTest {

  @Autowired private ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    assertThat(applicationContext).isNotNull();
    assertThat(applicationContext.getBean(TayJavApplication.class)).isNotNull();
  }
}
