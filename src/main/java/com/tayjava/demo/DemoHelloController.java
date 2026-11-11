package com.tayjava.demo;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Minimal endpoint so the request-lifecycle filter/interceptor demo can be exercised. */
@RestController
public class DemoHelloController {

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello from request lifecycle demo");
    }
}
