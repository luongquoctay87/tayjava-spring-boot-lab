package com.tayjava.demo.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.TayJavApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TayJavApplication.class)
@AutoConfigureMockMvc
class CatalogDemoTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CourseQueryService courseQueryService;

  @Test
  void frameworkServiceListsPublishedCourses() {
    assertThat(courseQueryService.listPublished())
        .extracting(CourseSummary::title)
        .containsExactly("Java Core", "Spring Boot from zero");
  }

  @Test
  void bootHostsMvcEndpoint() throws Exception {
    mockMvc.perform(get("/api/courses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Java Core"))
        .andExpect(jsonPath("$[1].level").value("INTERMEDIATE"));
  }
}
