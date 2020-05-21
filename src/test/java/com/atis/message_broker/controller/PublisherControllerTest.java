package com.atis.message_broker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PublisherControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void directRouteWithoutRoutingKey_returnsBadRequest() throws Exception {
        mvc.perform(post("/api/direct")
                .content("message")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void directRouteWithoutBody_returnsBadRequest() throws Exception {
        mvc.perform(post("/api/direct")
                .header("x-routing-key", "queue-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void directRouteWithRoutingKeyAndBody_returnsOK() throws Exception {
        mvc.perform(post("/api/direct")
                .header("x-routing-key", "queue-1")
                .content("this is the message")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}