package com.tayjava.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.tayjava.elearning.ElearningApiApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ElearningApiApplication.class)
class AopDemoTest {

    @Autowired
    private DemoEnrollmentService enrollmentService;

    @Autowired
    private TimingAspect timingAspect;

    @BeforeEach
    void resetAdviceCount() {
        timingAspect.reset();
    }

    @Test
    void enrollThroughProxyRunsAdviceOnce() {
        String result = enrollmentService.enroll("alice@example.com");

        assertThat(result).isEqualTo("enrolled:alice@example.com");
        assertThat(timingAspect.adviceCount()).isEqualTo(1);
    }

    @Test
    void selfInvocationRunsAdviceOnlyOnOuterCall() {
        String result = enrollmentService.enrollViaSelfInvocation("bob@example.com");

        assertThat(result).isEqualTo("enrolled:bob@example.com");
        // Outer method advised once; nested enroll(this) skips the proxy → still 1.
        assertThat(timingAspect.adviceCount()).isEqualTo(1);
    }
}
