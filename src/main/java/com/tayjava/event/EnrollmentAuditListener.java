package com.tayjava.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "EnrollmentAuditListener")
public class EnrollmentAuditListener {

  @EventListener
  public void onEnrolled(StudentEnrolledEvent event) {
    log.info(
        "AUDIT enroll id={} course={} email={}",
        event.enrollmentId(),
        event.courseId(),
        event.studentEmail());
  }
}
