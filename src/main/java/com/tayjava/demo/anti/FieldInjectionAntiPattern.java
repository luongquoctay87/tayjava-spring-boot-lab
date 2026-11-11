package com.tayjava.demo.anti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ANTI-PATTERN: field injection hides required dependencies and complicates tests.
 * Prefer constructor injection ({@code @RequiredArgsConstructor}) in real code.
 * Demo only — do not copy to production.
 */
@Component
public class FieldInjectionAntiPattern {

  @Autowired
  private FatEnrollmentController fatEnrollmentController;

  public FatEnrollmentController controller() {
    return fatEnrollmentController;
  }
}
