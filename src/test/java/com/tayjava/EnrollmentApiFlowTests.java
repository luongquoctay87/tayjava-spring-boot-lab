package com.tayjava;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class EnrollmentApiFlowTests extends AbstractPostgresIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void createsCourseThenEnrollsOverHttp() throws Exception {
    String slug = "spring-boot-flow-" + System.nanoTime();
    MvcResult created =
        mockMvc
            .perform(
                post("/api/courses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"title":"Spring Boot","slug":"%s","level":"BEGINNER","published":true}
                        """
                            .formatted(slug)))
            .andExpect(status().isCreated())
            .andReturn();
    long courseId = readId(created.getResponse().getContentAsString());

    mockMvc
        .perform(
            post("/api/courses/{id}/enrollments", courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"studentEmail":"alice@example.com"}
                    """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.courseId").value(courseId))
        .andExpect(jsonPath("$.data.status").value("ACTIVE"));

    mockMvc
        .perform(
            post("/api/courses/{id}/enrollments", courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"studentEmail":"alice@example.com"}
                    """))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("ALREADY_ENROLLED"));
  }

  private static long readId(String json) {
    int dataIdx = json.indexOf("\"data\"");
    int start = json.indexOf("\"id\":", dataIdx) + 5;
    int end = start;
    while (end < json.length() && Character.isDigit(json.charAt(end))) {
      end++;
    }
    return Long.parseLong(json.substring(start, end));
  }
}
