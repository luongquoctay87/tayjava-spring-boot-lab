package com.tayjava.event;

public record StudentEnrolledEvent(Long enrollmentId, Long courseId, String studentEmail) {}
