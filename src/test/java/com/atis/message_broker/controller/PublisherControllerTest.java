package com.atis.message_broker.controller;

import com.atis.message_broker.repository.StudentRepository;
import com.atis.message_broker.service.exchange.DirectExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PublisherControllerTest {

    @Autowired
    private MockMvc mvc;

    //TODO using two versions of same class?
    private DirectExchange directExchange;

    @MockBean
    private DirectExchange mockDirectExchange;



    @BeforeEach
    void init() {
        this.directExchange = new DirectExchange();
    }

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
    void exchangeGetsCalled() throws Exception {
        mvc.perform(post("/api/direct")
                .header("x-routing-key", "create-pdf")
                .content("this is the message")
                .contentType(MediaType.APPLICATION_JSON));

        verify(mockDirectExchange, times(1)).enqueue(isA(String.class), isA(String.class));
    }

    @Test
    void directRouteWithRoutingKeyAndBody_returnsOK() throws Exception {
        directExchange.registerQueue("create-pdf");

        mvc.perform(post("/api/direct")
                .header("x-routing-key", "create-pdf")
                .content("this is the message")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}