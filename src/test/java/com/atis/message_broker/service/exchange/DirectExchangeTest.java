package com.atis.message_broker.service.exchange;

import com.atis.message_broker.config.RedisConfig;
import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import com.atis.message_broker.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(classes = RedisConfig.class)
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