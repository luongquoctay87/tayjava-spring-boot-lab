package com.tayjava.demo;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Standalone IoC lifecycle demo outside the Boot app scan.
 * Uses the plain Framework container (no Boot) to observe constructor,
 * {@code @PostConstruct}, {@code @PreDestroy}, and prototype scope.
 */
class BeanLifecycleDemoTest {

  static final List<String> events = new ArrayList<>();

  @Component
  static class AuditTrail {
    AuditTrail() {
      events.add("AuditTrail: constructor");
    }
  }

  @Component
  static class LifecycleProbe {
    private final AuditTrail auditTrail;

    LifecycleProbe(AuditTrail auditTrail) {
      this.auditTrail = auditTrail;
      events.add("LifecycleProbe: constructor (AuditTrail received)");
    }

    @PostConstruct
    void init() {
      events.add("LifecycleProbe: @PostConstruct");
    }

    @PreDestroy
    void cleanup() {
      events.add("LifecycleProbe: @PreDestroy");
    }
  }

  @Component
  @Scope("prototype")
  static class DraftCart {
    @PreDestroy
    void cleanup() {
      events.add("DraftCart: @PreDestroy");
    }
  }

  @Test
  void lifecycleAndScopes() {
    events.clear();
    try (var context = new AnnotationConfigApplicationContext(
        AuditTrail.class, LifecycleProbe.class, DraftCart.class)) {
      events.add("context ready");
      assertThat(context.getBean(LifecycleProbe.class))
          .isSameAs(context.getBean(LifecycleProbe.class));
      assertThat(context.getBean(DraftCart.class))
          .isNotSameAs(context.getBean(DraftCart.class));
    }
    assertThat(events).containsExactly(
        "AuditTrail: constructor",
        "LifecycleProbe: constructor (AuditTrail received)",
        "LifecycleProbe: @PostConstruct",
        "context ready",
        "LifecycleProbe: @PreDestroy");
    // Prototype DraftCart never receives @PreDestroy
    assertThat(events).doesNotContain("DraftCart: @PreDestroy");
  }
}
