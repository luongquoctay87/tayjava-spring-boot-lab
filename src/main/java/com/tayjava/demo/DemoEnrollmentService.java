package com.tayjava.demo;

import org.springframework.stereotype.Service;

/**
 * Demo service for lesson 42 AOP. {@link #enroll(String)} is advised when called
 * through the Spring proxy; {@link #enrollViaSelfInvocation(String)} shows that an
 * internal call does not go through the proxy (advice does not wrap the inner call).
 */
@Service
public class DemoEnrollmentService {

  public String enroll(String email) {
    return "enrolled:" + email;
  }

  /**
   * Self-invocation: the outer method is advised; the nested {@link #enroll(String)}
   * call stays on {@code this} and skips the aspect.
   */
  public String enrollViaSelfInvocation(String email) {
    return enroll(email);
  }
}
