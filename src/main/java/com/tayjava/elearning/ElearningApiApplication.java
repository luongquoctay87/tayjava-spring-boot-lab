package com.tayjava.elearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Demo branch entry: scans the catalog demo under {@code com.tayjava.demo.catalog}
 * so Framework business code runs inside a Boot host.
 * Side branch only — do not merge into the Course API project line.
 */
@SpringBootApplication(scanBasePackages = {"com.tayjava.elearning", "com.tayjava.demo.catalog"})
public class ElearningApiApplication {

    public static void main(String[] args) {
        // Boot: create the ApplicationContext, run auto-config, start the embedded server
        SpringApplication.run(ElearningApiApplication.class, args);
    }
}
