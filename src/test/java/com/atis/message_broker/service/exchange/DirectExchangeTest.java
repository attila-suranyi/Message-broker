package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
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

    @Test
    void queueRegistered() {
        exchange.registerQueue("create-pdf");
        assertNotNull(exchange.getQueue("create-pdf"));
    }

    @Test
    void messageSaved() throws IncorrectRoutingKeyException {
        exchange.registerQueue("create-pdf");
        exchange.enqueue("create-pdf", "message");
        assertEquals(1, exchange.getQueue("create-pdf").size());
    }
}