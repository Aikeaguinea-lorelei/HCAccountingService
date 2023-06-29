package com.hardcore.accounting.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HelloControllerTest {
    // MockMvc: 测试Controller时需要用到的
    private MockMvc mockMvc;
    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }
    @Test
    void testSayHello() throws Exception{
        // Arrange & Act & Assert

        // param()就是Arrange阶段准备数据,perform()就是Act阶段,act的内容是抛出Exception异常
        // andExpect()就是Assert的断言阶段
        mockMvc.perform(get("/v1.0/greeting").param("name","world"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"hello, world\"}"));
    }
}
