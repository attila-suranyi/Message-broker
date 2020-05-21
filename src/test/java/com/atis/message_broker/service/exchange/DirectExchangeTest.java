package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;


@SpringBootTest
class DirectExchangeTest {

    private DirectExchange exchange;

    @BeforeEach
    void init() {
        this.exchange = new DirectExchange();
    }

    @Test
    void incorrectRoutingKeyThrowsException() {
        assertThrows(IncorrectRoutingKeyException.class,
                () -> exchange.enqueue("routing key 1", "message"));
    }

}