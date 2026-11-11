package com.tayjava.event;

import com.tayjava.service.EnrollmentNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EnrollmentMailListener {

  private final EnrollmentNotificationService notificationService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onEnrolled(StudentEnrolledEvent event) {
    notificationService.sendEnrollmentConfirmation(event.enrollmentId(), event.studentEmail());
  }
}
