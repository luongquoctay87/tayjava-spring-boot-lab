package com.tayjava.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Registers the demo interceptor for {@code /api/**}. */
@Configuration
public class DemoWebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerLoggingInterceptor()).addPathPatterns("/api/**");
  }
}
