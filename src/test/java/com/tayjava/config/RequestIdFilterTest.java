package com.tayjava.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tayjava.support.AbstractPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class RequestIdFilterTest extends AbstractPostgresIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void echoesProvidedRequestIdHeader() throws Exception {
    mockMvc
        .perform(get("/actuator/health").header(RequestIdFilter.HEADER, "demo-42"))
        .andExpect(status().isOk())
        .andExpect(header().string(RequestIdFilter.HEADER, "demo-42"));
  }
}
