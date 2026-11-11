package com.tayjava.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Jackson 3 / Java Time demo endpoint. Temporary — not part of the Course API line.
 */
@RestController
public class ClockDemoController {

    public record ClockSample(
            Instant instant,
            LocalDate date,
            LocalDateTime localDateTime,
            Duration ttl,
            @JsonFormat(pattern = "dd/MM/yyyy") LocalDate displayDate) {}

    @GetMapping("/demo/clock")
    public ClockSample clock() {
        LocalDate date = LocalDate.of(2026, 11, 10);
        return new ClockSample(
                Instant.parse("2026-11-09T20:15:30.123456Z"),
                date,
                LocalDateTime.of(2026, 11, 10, 3, 15, 30),
                Duration.ofMinutes(5),
                date);
    }
}
