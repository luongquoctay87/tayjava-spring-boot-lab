package com.tayjava.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
@TestPropertySource(
    properties = {
      "tayjava.hello.message=Hello from tayjava-spring-boot-lab",
      "spring.application.name=tayjava-spring-boot-lab"
    })
class HelloControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void helloReturnsMessage() throws Exception {
    mockMvc
        .perform(get("/api/hello"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Hello from tayjava-spring-boot-lab"))
        .andExpect(jsonPath("$.app").value("tayjava-spring-boot-lab"));
  }
}
