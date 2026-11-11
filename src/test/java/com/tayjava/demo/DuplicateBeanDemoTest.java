package com.tayjava.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

/**
 * Two beans of the same type: ambiguity, {@code @Qualifier}, and {@code @Primary}.
 */
class DuplicateBeanDemoTest {

  @Configuration(proxyBeanMethods = false)
  static class HttpClientsConfig {
    @Bean
    RestClient catalogRestClient() {
      return RestClient.builder().baseUrl("https://catalog.example.com").build();
    }

    @Bean
    RestClient paymentRestClient() {
      return RestClient.builder().baseUrl("https://payment.example.com").build();
    }
  }

  static class PaymentGateway {
    final RestClient restClient;

    PaymentGateway(@Qualifier("paymentRestClient") RestClient restClient) {
      this.restClient = restClient;
    }
  }

  @Configuration(proxyBeanMethods = false)
  static class PrimaryConfig {
    @Bean
    @Primary
    RestClient catalogRestClient() {
      return RestClient.create("https://catalog.example.com");
    }

    @Bean
    RestClient paymentRestClient() {
      return RestClient.create("https://payment.example.com");
    }
  }

  @Test
  void twoBeansOfSameTypeAreAmbiguous() {
    try (var context = new AnnotationConfigApplicationContext(HttpClientsConfig.class)) {
      assertThatThrownBy(() -> context.getBean(RestClient.class))
          .isInstanceOf(NoUniqueBeanDefinitionException.class)
          .hasMessageContaining("expected single matching bean but found 2");
    }
  }

  @Test
  void qualifierPicksByName() {
    try (var context =
        new AnnotationConfigApplicationContext(HttpClientsConfig.class, PaymentGateway.class)) {
      assertThat(context.getBean(PaymentGateway.class).restClient)
          .isSameAs(context.getBean("paymentRestClient"));
    }
  }

  @Test
  void primaryWinsWhenNoQualifier() {
    try (var context = new AnnotationConfigApplicationContext(PrimaryConfig.class)) {
      assertThat(context.getBean(RestClient.class)).isSameAs(context.getBean("catalogRestClient"));
    }
  }
}
