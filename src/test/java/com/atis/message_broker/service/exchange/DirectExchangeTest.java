package com.atis.message_broker.service.exchange;

import com.atis.message_broker.config.ReactiveRedisConfig;
import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(classes = ReactiveRedisConfig.class)
class DirectExchangeTest {

    private DirectExchange exchange;

    @Qualifier("reactiveRedisTemplateString")
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;
    private ReactiveListOperations<String, String> reactiveListOps;


    @BeforeEach
    void init() {
        this.exchange = new DirectExchange();
        reactiveListOps = redisTemplate.opsForList();
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

    @Test
    void redisTest() {
        reactiveListOps.leftPush("key", "message");
        assertNotNull(reactiveListOps.leftPop("key"));
    }
}