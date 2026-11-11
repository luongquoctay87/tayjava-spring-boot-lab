package com.tayjava.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Declaring a JDK {@link Clock} with {@code @Bean} and injecting it by constructor.
 */
class ClockBeanDemoTest {

    @Configuration
    static class TimeConfig {
        @Bean
        Clock clock() {
            return Clock.systemUTC();
        }
    }

    @Component
    static class EnrollmentWindow {
        private final Clock clock;

        EnrollmentWindow(Clock clock) {
            this.clock = clock;
        }

        boolean isOpen(Instant opensAt, Instant closesAt) {
            Instant now = clock.instant();
            return !now.isBefore(opensAt) && now.isBefore(closesAt);
        }
    }

    @Test
    void containerInjectsTheClockBean() {
        try (var context = new AnnotationConfigApplicationContext(TimeConfig.class, EnrollmentWindow.class)) {
            assertThat(context.getBean("clock")).isSameAs(context.getBean(Clock.class));
            assertThat(context.getBean(EnrollmentWindow.class)).isNotNull();
        }
    }

    @Test
    void fixedClockMakesTimeTestable() {
        Clock fixed = Clock.fixed(Instant.parse("2026-11-10T09:00:00Z"), ZoneOffset.UTC);
        EnrollmentWindow window = new EnrollmentWindow(fixed);

        assertThat(window.isOpen(
                        Instant.parse("2026-11-01T00:00:00Z"), Instant.parse("2026-11-30T00:00:00Z")))
                .isTrue();
        assertThat(window.isOpen(
                        Instant.parse("2026-12-01T00:00:00Z"), Instant.parse("2026-12-31T00:00:00Z")))
                .isFalse();
    }
}
