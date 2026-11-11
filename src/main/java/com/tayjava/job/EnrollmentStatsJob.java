package com.tayjava.job;

import com.tayjava.common.enums.EEnrollmentStatus;
import com.tayjava.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j(topic = "EnrollmentStatsJob")
public class EnrollmentStatsJob {

  private final EnrollmentRepository enrollments;

  @Scheduled(fixedDelay = 60_000)
  public void logActiveCount() {
    long active = enrollments.countByStatus(EEnrollmentStatus.ACTIVE);
    long cancelled = enrollments.countByStatus(EEnrollmentStatus.CANCELLED);
    log.info("Enrollment stats: active={}, cancelled={}", active, cancelled);
  }
}
