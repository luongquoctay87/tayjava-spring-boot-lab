package com.tayjava.elearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Side-branch demo host for anti-pattern examples under {@code com.tayjava.demo}.
 * Do not merge into the Course API project line.
 */
@SpringBootApplication(scanBasePackages = {"com.tayjava.elearning", "com.tayjava.demo"})
public class ElearningApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElearningApiApplication.class, args);
    }
}
