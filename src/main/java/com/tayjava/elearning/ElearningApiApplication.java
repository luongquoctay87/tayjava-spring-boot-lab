package com.tayjava.elearning;

import com.tayjava.demo.ClockDemoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Side-branch host for Jackson demos under {@code com.tayjava.demo}.
 * Do not merge into the Course API project line.
 */
@SpringBootApplication
@Import(ClockDemoController.class)
public class ElearningApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElearningApiApplication.class, args);
    }
}
