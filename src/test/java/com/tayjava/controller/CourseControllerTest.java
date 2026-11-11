package com.tayjava.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.common.dto.course.CourseResp;
import com.tayjava.common.enums.ECourseLevel;
import com.tayjava.service.CourseService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
  void getReturnsCourse() throws Exception {
    when(courseService.get(1L))
        .thenReturn(new CourseResp(1L, "Spring Boot", "spring-boot", ECourseLevel.BEGINNER, true));

    mockMvc
        .perform(get("/api/courses/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void deleteReturnsOk() throws Exception {
    mockMvc.perform(delete("/api/courses/1")).andExpect(status().isOk());
  }
}
