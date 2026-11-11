package com.tayjava.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

  @Bean(name = "enrollmentExecutor")
  public Executor enrollmentExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (ex, method, params) ->
        LoggerFactory.getLogger(method.getDeclaringClass())
            .error("Async error in {}", method.getName(), ex);
  }
}
