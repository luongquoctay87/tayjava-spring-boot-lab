package com.tayjava.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.common.dto.enrollment.EnrollmentResp;
import com.tayjava.common.enums.EEnrollmentStatus;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.common.exception.GlobalExceptionHandling;
import com.tayjava.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EnrollmentController.class)
@Import(GlobalExceptionHandling.class)
class EnrollmentControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private EnrollmentService enrollmentService;

  @Test
  void enrollReturns201WithLocation() throws Exception {
    when(enrollmentService.enroll(eq(1L), eq("alice@example.com")))
        .thenReturn(new EnrollmentResp(10L, 1L, "alice@example.com", EEnrollmentStatus.ACTIVE));

    mockMvc
        .perform(
            post("/api/courses/1/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"studentEmail":"alice@example.com"}
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/enrollments/10"))
        .andExpect(jsonPath("$.data.status").value("ACTIVE"));
  }

  @Test
  void enrollDuplicateReturns409() throws Exception {
    when(enrollmentService.enroll(eq(1L), eq("alice@example.com")))
        .thenThrow(
            new ErrorException(ErrorCode.ALREADY_ENROLLED, "Already enrolled in course 1"));

    mockMvc
        .perform(
            post("/api/courses/1/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"studentEmail":"alice@example.com"}
                    """))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("ALREADY_ENROLLED"));
  }
}
