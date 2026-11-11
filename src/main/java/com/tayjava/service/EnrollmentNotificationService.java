package com.tayjava.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "EnrollmentNotificationService")
public class EnrollmentNotificationService {

  @Async("enrollmentExecutor")
  public void sendEnrollmentConfirmation(Long enrollmentId, String email) {
    log.info("Sending confirmation for enrollment {} to {}", enrollmentId, email);
  }
}
