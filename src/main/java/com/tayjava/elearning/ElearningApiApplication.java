package com.tayjava.elearning;

import com.tayjava.demo.DemoJpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Side-branch host for the Spring Data JPA intro demo under {@code com.tayjava.demo}.
 * Do not merge into the Course API project line.
 */
@SpringBootApplication
@Import(DemoJpaConfig.class)
public class ElearningApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElearningApiApplication.class, args);
    }
}
