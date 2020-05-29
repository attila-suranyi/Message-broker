package com.atis.message_broker;

import com.atis.message_broker.config.RedisConfig;
import com.atis.message_broker.model.DirectMessage;
import com.atis.message_broker.repository.CustomMessageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import redis.embedded.RedisServer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = RedisConfig.class)
@ComponentScan(basePackages = {"com.atis.message_broker"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RedisTest {

    private CustomMessageRepository repository;
    private RedisServer redisServer;


    @Autowired
    @Qualifier("flushRedisTemplate")
    private RedisTemplate<String, DirectMessage> flushRedisTemplate;

    @Autowired
    private void init(CustomMessageRepository repository) {
        this.repository = repository;
    }

    @Before
    public void setup() {
        redisServer = new RedisServer(6390);
        redisServer.start();
    }

    @After
    public void tearDown() {
        redisServer.stop();
    }

    @AfterEach
    void cleanUp() {
        flushRedisTemplate.execute((RedisCallback<Void>) connection -> {
            connection.flushAll();
            return null;
        });
    }


    @Test
    void messagePersisted() {
        DirectMessage message = new DirectMessage("create_pdf", "This is the pdf content");
        repository.save(message);
        DirectMessage messageRetrieved = repository.findById(message.getId()).get();
        assertEquals(message, messageRetrieved);
    }

    @Test
    void messageCorrectOrder() {
        List<DirectMessage> messages = Arrays.asList(
                new DirectMessage("create-pdf", "content1"),
                new DirectMessage("create-pdf", "content2"),
                new DirectMessage("create-pdf", "content3")
        );
        flushRedisTemplate.opsForList().leftPushAll("create-pdf", messages);
        assertEquals("content3", flushRedisTemplate.opsForList().leftPop("create-pdf").getContent());
    }

    @Test
    void redisFlushed() {
        DirectMessage message1 = new DirectMessage("flushed", "content");
        ListOperations<String, DirectMessage> simpleList = flushRedisTemplate.opsForList();

        simpleList.leftPush(message1.getRoutingKey(), message1);
        flushRedisTemplate.execute((RedisCallback<Void>) connection -> {
            connection.flushDb();
            return null;
        });
        assertNull(simpleList.leftPop("flushed"));
    }


    @Test
    void newQueueCreated() {
        DirectMessage message1 = new DirectMessage("create-pdf", "content1");
        DirectMessage message2 = new DirectMessage("create-pdf", "content2");
        DirectMessage message3 = new DirectMessage("something else", "content3");

        ListOperations<String, DirectMessage> simpleList = flushRedisTemplate.opsForList();

        Long num1 = simpleList.leftPush(message1.getRoutingKey(), message1);
        Long num2 = simpleList.leftPush(message2.getRoutingKey(), message2);
        Long num3 = simpleList.leftPush(message3.getRoutingKey(), message3);

        flushRedisTemplate.execute((RedisCallback<Void>) connection -> {
            connection.flushDb();
            return null;
        });

        assertNull(simpleList.leftPop("create-pdf"));
    }
}
