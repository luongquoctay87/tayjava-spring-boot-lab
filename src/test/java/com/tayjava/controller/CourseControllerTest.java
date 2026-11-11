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
import com.tayjava.service.CourseService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(CourseController.class)
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
        .andExpect(jsonPath("$[0].slug").value("spring-boot"));
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
        .andExpect(jsonPath("$.slug").value("spring-boot"));
  }

  @Test
  void createDuplicateSlugReturns409() throws Exception {
    when(courseService.create(any(CourseReq.class)))
        .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Slug already exists"));

    mockMvc
        .perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"Spring Boot","slug":"spring-boot","level":"BEGINNER","published":true}
                    """))
        .andExpect(status().isConflict());
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
        .andExpect(jsonPath("$.title").value("Spring Boot 4"));
  }

  @Test
  void deleteReturns204() throws Exception {
    mockMvc.perform(delete("/api/courses/1")).andExpect(status().isNoContent());
  }

  @Test
  void deleteMissingReturns404() throws Exception {
    doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found: 99"))
        .when(courseService)
        .delete(99L);

    mockMvc.perform(delete("/api/courses/99")).andExpect(status().isNotFound());
  }
}
