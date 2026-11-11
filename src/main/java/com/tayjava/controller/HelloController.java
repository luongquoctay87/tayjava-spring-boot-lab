package com.tayjava.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  public record HelloResponse(String message, String app) {}

  private final String message;
  private final String appName;

  public HelloController(
      @Value("${tayjava.hello.message}") String message,
      @Value("${spring.application.name}") String appName) {
    this.message = message;
    this.appName = appName;
  }

  @GetMapping("/api/hello")
  public HelloResponse hello() {
    return new HelloResponse(message, appName);
  }
}
