package com.tayjava.elearning;

import com.tayjava.demo.DemoHelloController;
import com.tayjava.demo.DemoWebConfig;
import com.tayjava.demo.RequestTimingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Side-branch host: temporarily imports request-lifecycle demo components.
 * Do not merge into the Course API project line / lab / capstone.
 */
@SpringBootApplication
@Import({RequestTimingFilter.class, DemoWebConfig.class, DemoHelloController.class})
public class ElearningApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElearningApiApplication.class, args);
    }
}
