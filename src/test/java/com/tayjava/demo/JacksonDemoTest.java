package com.tayjava.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.elearning.ElearningApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

@SpringBootTest(classes = ElearningApiApplication.class)
@AutoConfigureMockMvc
class JacksonDemoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void clockEndpointSerializesJavaTime() throws Exception {
        mockMvc.perform(get("/demo/clock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instant").value("2026-11-09T20:15:30.123456Z"))
                .andExpect(jsonPath("$.date").value("2026-11-10"))
                .andExpect(jsonPath("$.localDateTime").value("2026-11-10T03:15:30"))
                .andExpect(jsonPath("$.ttl").value("PT5M"))
                .andExpect(jsonPath("$.displayDate").value("10/11/2026"));
    }

    @Test
    void jsonPropertyUsesSnakeCaseName() throws Exception {
        SnakeCourseId fromSnake = jsonMapper.readValue("{\"course_id\":5}", SnakeCourseId.class);
        assertThat(fromSnake.courseId()).isEqualTo(5L);

        SnakeCourseId fromCamel = jsonMapper.readValue("{\"courseId\":5}", SnakeCourseId.class);
        assertThat(fromCamel.courseId()).isNull();
    }
}
