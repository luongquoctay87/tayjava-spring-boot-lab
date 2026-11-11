package com.tayjava.config;

import java.time.Duration;
import org.springframework.boot.restclient.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;

@Configuration
public class HttpClientConfig {

  @Bean
  RestClientCustomizer couponTimeoutCustomizer() {
    return builder -> {
      JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory();
      factory.setReadTimeout(Duration.ofSeconds(3));
      builder.requestFactory(factory);
    };
  }
}
