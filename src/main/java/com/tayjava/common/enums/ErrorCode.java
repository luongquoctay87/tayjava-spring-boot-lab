package com.tayjava.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Canonical API/business error codes mapped to HTTP status values (lab subset).
 * Lesson 24 locks the Course contract around {@code VALIDATION_ERROR}, {@code COURSE_NOT_FOUND},
 * and {@code SLUG_TAKEN}; enrollment codes are used from lesson 27 onward.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
  SUCCESS("Success", HttpStatus.OK),

  INVALID_REQUEST("Invalid request", HttpStatus.BAD_REQUEST),
  VALIDATION_ERROR("Validation failed", HttpStatus.BAD_REQUEST),

  COURSE_NOT_FOUND("Course not found", HttpStatus.NOT_FOUND),
  ENROLLMENT_NOT_FOUND("Enrollment not found", HttpStatus.NOT_FOUND),

  DUPLICATE_RESOURCE("Resource already exists", HttpStatus.CONFLICT),
  SLUG_TAKEN("Slug already exists", HttpStatus.CONFLICT),
  ALREADY_ENROLLED("Already enrolled", HttpStatus.CONFLICT),
  COURSE_NOT_PUBLISHED("Course is not published", HttpStatus.CONFLICT),
  COURSE_HAS_ENROLLMENTS("Course has enrollments", HttpStatus.CONFLICT);

  private final String message;
  private final HttpStatus httpStatus;
}
