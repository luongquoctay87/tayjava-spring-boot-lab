package com.tayjava.demo.anti;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.elearning.ElearningApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ElearningApiApplication.class)
@AutoConfigureMockMvc
class AntiPatternDemoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FieldInjectionAntiPattern fieldInjectionAntiPattern;

    @Test
    void fieldInjectionWireIsPresentForDemoOnly() {
        assertThat(fieldInjectionAntiPattern.controller()).isNotNull();
    }

    @Test
    void fatControllerEnrollsButIsNotAPatternToCopy() throws Exception {
        mockMvc.perform(post("/api/courses/1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentEmail\":\"alice@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentEmail").value("alice@example.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}
