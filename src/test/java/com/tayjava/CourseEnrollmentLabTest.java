package com.tayjava;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
class CourseEnrollmentLabTest extends AbstractPostgresIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void courseAndEnrollmentCrudHappyPathAndConflicts() throws Exception {
    String slug = "lab-course-" + System.nanoTime();
    MvcResult created =
        mockMvc
            .perform(
                post("/api/courses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"title":"Spring Boot Lab","slug":"%s","level":"BEGINNER","published":true}
                        """
                            .formatted(slug)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern("/api/courses/\\d+")))
            .andExpect(jsonPath("$.data.slug").value(slug))
            .andReturn();
    long courseId = readId(created.getResponse().getContentAsString());

    mockMvc
        .perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"Spring Boot Lab","slug":"%s","level":"BEGINNER","published":true}
                    """
                        .formatted(slug)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("SLUG_TAKEN"));

    mockMvc
        .perform(get("/api/courses/{id}", courseId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(courseId));

    mockMvc
        .perform(get("/api/courses").param("size", "50"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.data[?(@.id==%d)]".formatted(courseId)).exists());

    mockMvc
        .perform(
            put("/api/courses/{id}", courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"Spring Boot Lab Updated","level":"INTERMEDIATE","published":true}
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value("Spring Boot Lab Updated"));

    String draftSlug = "lab-draft-" + System.nanoTime();
    MvcResult draft =
        mockMvc
            .perform(
                post("/api/courses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"title":"Draft","slug":"%s","level":"BEGINNER","published":false}
                        """
                            .formatted(draftSlug)))
            .andExpect(status().isCreated())
            .andReturn();
    long draftId = readId(draft.getResponse().getContentAsString());

    mockMvc
        .perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"title":"","slug":"Bad Slug","level":"EXPERT","published":true}
                    """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

    MvcResult enrolled =
        mockMvc
            .perform(
                post("/api/courses/{courseId}/enrollments", courseId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"studentEmail":"alice@example.com"}
                        """))
            .andExpect(status().isCreated())
            .andExpect(
                header()
                    .string(
                        "Location",
                        org.hamcrest.Matchers.matchesPattern("/api/enrollments/\\d+")))
            .andExpect(jsonPath("$.data.status").value("ACTIVE"))
            .andReturn();
    long enrollmentId = readId(enrolled.getResponse().getContentAsString());

    mockMvc
        .perform(
            post("/api/courses/{courseId}/enrollments", courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"studentEmail":"alice@example.com"}
                    """))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("ALREADY_ENROLLED"));

    mockMvc
        .perform(
            post("/api/courses/{courseId}/enrollments", draftId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"studentEmail":"alice@example.com"}
                    """))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("COURSE_NOT_PUBLISHED"));

    mockMvc
        .perform(get("/api/enrollments").param("studentEmail", "alice@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[?(@.id==%d)]".formatted(enrollmentId)).exists());

    mockMvc
        .perform(get("/api/enrollments/{id}", enrollmentId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.studentEmail").value("alice@example.com"));

    mockMvc
        .perform(post("/api/enrollments/{id}/cancel", enrollmentId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.status").value("CANCELLED"));

    mockMvc
        .perform(get("/api/courses/999999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("COURSE_NOT_FOUND"));

    mockMvc
        .perform(get("/api/enrollments/999999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("ENROLLMENT_NOT_FOUND"));

    mockMvc
        .perform(delete("/api/courses/{id}", courseId))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("COURSE_HAS_ENROLLMENTS"));

    mockMvc.perform(delete("/api/courses/{id}", draftId)).andExpect(status().isNoContent());
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
