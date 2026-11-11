package com.tayjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for the TayJava Spring Boot lab (Course / Enrollment series).
 * Lesson branches grow from {@code master} (lesson 05 skeleton).
 */
@SpringBootApplication
@EnableScheduling
public class TayJavApplication {

  public static void main(String[] args) {
    SpringApplication.run(TayJavApplication.class, args);
  }
}
