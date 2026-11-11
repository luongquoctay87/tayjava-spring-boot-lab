package com.tayjava.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.common.dto.course.CourseReq;
import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.dto.course.CourseUpdateReq;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.common.enums.ErrorCode;
import com.tayjava.common.exception.ErrorException;
import com.tayjava.common.exception.GlobalExceptionHandling;
import com.tayjava.service.CourseService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CourseController.class)
@Import(GlobalExceptionHandling.class)
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private CourseService courseService;

  @Test
  void listReturnsCourses() throws Exception {
    when(courseService.list())
        .thenReturn(
            List.of(new CourseResp(1L, "Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true)));

    mockMvc
        .perform(get("/api/courses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data[0].slug").value("spring-boot"));
  }

  @Test
  void getReturnsCourse() throws Exception {
    when(courseService.get(1L))
        .thenReturn(new CourseResp(1L, "Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true));

    mockMvc
        .perform(get("/api/courses/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(1));
  }

  @Test
  void getMissingReturns404WithCode() throws Exception {
    when(courseService.get(99L))
        .thenThrow(new ErrorException(ErrorCode.COURSE_NOT_FOUND, "Course not found: 99"));

    mockMvc
        .perform(get("/api/courses/99"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.code").value("COURSE_NOT_FOUND"));
  }

  @Test
  void createReturns201WithLocation() throws Exception {
    when(courseService.create(any(CourseReq.class)))
        .thenReturn(new CourseResp(1L, "Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true));

    mockMvc
        .perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"Spring Boot","slug":"spring-boot","level":"BEGINNER","published":true}
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/courses/1"))
        .andExpect(jsonPath("$.data.slug").value("spring-boot"));
  }

  @Test
  void createDuplicateSlugReturns409WithCode() throws Exception {
    when(courseService.create(any(CourseReq.class)))
        .thenThrow(new ErrorException(ErrorCode.SLUG_TAKEN, "Slug already exists: spring-boot"));

    mockMvc
        .perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"Spring Boot","slug":"spring-boot","level":"BEGINNER","published":true}
                    """))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("SLUG_TAKEN"));
  }

  @Test
  void createWithInvalidBodyReturnsValidationError() throws Exception {
    mockMvc
        .perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"","slug":"Bad Slug","level":"BEGINNER","published":true}
                    """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.data.title").exists())
        .andExpect(jsonPath("$.data.slug").exists());
  }

  @Test
  void createWithInvalidEnumReturnsValidationError() throws Exception {
    mockMvc
        .perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"Spring Boot","slug":"spring-boot","level":"EXPERT","published":true}
                    """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
  }

  @Test
  void updateAcceptsPathAndBody() throws Exception {
    when(courseService.update(eq(1L), any(CourseUpdateReq.class)))
        .thenReturn(
            new CourseResp(1L, "Spring Boot 4", "spring-boot", ECourseLevel.INTERMEDIATE, true));

    mockMvc
        .perform(
            put("/api/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"Spring Boot 4","level":"INTERMEDIATE","published":true}
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value("Spring Boot 4"));
  }

  @Test
  void deleteReturns204() throws Exception {
    mockMvc.perform(delete("/api/courses/1")).andExpect(status().isNoContent());
  }

  @Test
  void deleteMissingReturns404WithCode() throws Exception {
    doThrow(new ErrorException(ErrorCode.COURSE_NOT_FOUND, "Course not found: 99"))
        .when(courseService)
        .delete(99L);

    mockMvc
        .perform(delete("/api/courses/99"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("COURSE_NOT_FOUND"));
  }
}
