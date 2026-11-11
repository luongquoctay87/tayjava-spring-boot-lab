package com.tayjava.common.exception;

import com.tayjava.common.dto.Resp;
import com.tayjava.common.enums.ErrorCode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Maps framework and application exceptions to {@link Resp} payloads with stable {@link ErrorCode}
 * values.
 */
@RestControllerAdvice
@Slf4j(topic = "GLOBAL-EXCEPTION")
public class GlobalExceptionHandling {

  @ExceptionHandler(ErrorException.class)
  public ResponseEntity<Resp<Void>> handleBusiness(ErrorException ex) {
    log.warn("Business error {}: {}", ex.getErrorCode().name(), ex.getMessage());
    return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
        .body(Resp.error(ex.getErrorCode(), ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Resp<Map<String, String>>> handleValidation(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    err -> String.valueOf(err.getDefaultMessage()),
                    (first, second) -> first,
                    LinkedHashMap::new));
    String message =
        errors.entrySet().stream()
            .map(e -> e.getKey() + " " + e.getValue())
            .collect(Collectors.joining("; "));
    log.warn("Validation error: {}", message);
    return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
        .body(
            Resp.<Map<String, String>>builder()
                .success(false)
                .code(ErrorCode.VALIDATION_ERROR.name())
                .message(message.isBlank() ? ErrorCode.VALIDATION_ERROR.getMessage() : message)
                .data(errors)
                .build());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Resp<Void>> handleUnreadable(HttpMessageNotReadableException ex) {
    String message = "Malformed JSON or invalid request body";
    Throwable root = ex.getMostSpecificCause();
    if (root != null && root.getMessage() != null && !root.getMessage().isBlank()) {
      message = root.getMessage();
    }
    log.warn("Unreadable body: {}", message);
    return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
        .body(Resp.error(ErrorCode.VALIDATION_ERROR, message));
  }
}
