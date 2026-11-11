package com.tayjava.elearning;

import com.tayjava.demo.DemoEnrollmentService;
import com.tayjava.demo.TimingAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Side-branch host: temporarily imports AOP demo components.
 * Do not merge into the Course API project line / lab / capstone.
 */
@SpringBootApplication
@Import({TimingAspect.class, DemoEnrollmentService.class})
public class ElearningApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElearningApiApplication.class, args);
    }
}
