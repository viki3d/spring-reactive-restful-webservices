package com.viki3d.spring.reactive.restful.webservices.front.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test for IndexController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testIndex() throws Exception {
    this.mockMvc
        .perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Cars API")));
  }

  @Test
  public void testGetCachedCar() throws Exception {
    this.mockMvc
        .perform(get("/getcache/car"))
        //.andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("{}")));
  }

}
