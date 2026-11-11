package com.tayjava.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shows how {@code proxyBeanMethods} affects inner {@code @Bean} method calls.
 */
class ConfigurationProxyDemoTest {

    static class AuditLog {}

    record EnrollmentNotifier(AuditLog auditLog) {}

    @Configuration
    static class FullConfig {
        @Bean
        AuditLog auditLog() {
            return new AuditLog();
        }

        @Bean
        EnrollmentNotifier enrollmentNotifier() {
            return new EnrollmentNotifier(auditLog());
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class LiteConfig {
        @Bean
        AuditLog auditLog() {
            return new AuditLog();
        }

        @Bean
        EnrollmentNotifier enrollmentNotifier() {
            return new EnrollmentNotifier(auditLog());
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class ParameterConfig {
        @Bean
        AuditLog auditLog() {
            return new AuditLog();
        }

        @Bean
        EnrollmentNotifier enrollmentNotifier(AuditLog auditLog) {
            return new EnrollmentNotifier(auditLog);
        }
    }

    @Test
    void fullConfigurationReturnsTheManagedBean() {
        try (var context = new AnnotationConfigApplicationContext(FullConfig.class)) {
            assertThat(context.getBean(EnrollmentNotifier.class).auditLog())
                    .isSameAs(context.getBean(AuditLog.class));
        }
    }

    @Test
    void proxyBeanMethodsFalseCreatesAnotherObject() {
        try (var context = new AnnotationConfigApplicationContext(LiteConfig.class)) {
            assertThat(context.getBean(EnrollmentNotifier.class).auditLog())
                    .isNotSameAs(context.getBean(AuditLog.class));
        }
    }

    @Test
    void methodParameterAlwaysGetsTheManagedBean() {
        try (var context = new AnnotationConfigApplicationContext(ParameterConfig.class)) {
            assertThat(context.getBean(EnrollmentNotifier.class).auditLog())
                    .isSameAs(context.getBean(AuditLog.class));
        }
    }
}
