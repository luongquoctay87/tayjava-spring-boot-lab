package com.tayjava.common.dto;

import com.tayjava.common.enums.ErrorCode;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** Factory for {@link ResponseEntity} wrapping {@link Resp} with conventional HTTP status codes. */
public final class ApiResp {

  private ApiResp() {}

  public static <T> ResponseEntity<Resp<T>> ok(T data) {
    return ResponseEntity.ok(
        Resp.<T>builder()
            .success(true)
            .code("SUCCESS")
            .data(data)
            .message("Data retrieved successfully")
            .build());
  }

  public static ResponseEntity<Resp<Void>> ok() {
    return ResponseEntity.ok(Resp.<Void>builder().success(true).code("SUCCESS").build());
  }

  public static <T> ResponseEntity<Resp<T>> created(T data) {
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBody(data));
  }

  /** 201 Created with {@code Location} header and payload. */
  public static <T> ResponseEntity<Resp<T>> created(URI location, T data) {
    return ResponseEntity.created(location).body(createdBody(data));
  }

  private static <T> Resp<T> createdBody(T data) {
    return Resp.<T>builder()
        .success(true)
        .code("SUCCESS")
        .message("Data saved successfully")
        .data(data)
        .build();
  }

  public static ResponseEntity<Resp<Void>> noContent() {
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(
            Resp.<Void>builder()
                .success(true)
                .code("SUCCESS")
                .message("Data deleted successfully")
                .build());
  }

  public static <T> ResponseEntity<Resp<T>> error(ErrorCode code, String message) {
    return ResponseEntity.status(code.getHttpStatus())
        .body(Resp.<T>builder().success(false).code(code.name()).message(message).build());
  }
}
