package com.atis.message_broker;

import com.atis.message_broker.config.RedisConfig;
import com.atis.message_broker.model.DirectMessage;
import com.atis.message_broker.repository.CustomMessageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import redis.embedded.RedisServer;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ContextConfiguration(classes = TestRedisConfiguration.class)
@ComponentScan(basePackages = {"com.atis.message_broker"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RedisTest {

    private CustomMessageRepository repository;

    private RedisTemplate<String, DirectMessage> template;


    //private RedisServer redisServer;


    /*@Autowired
    @Qualifier("flushRedisTemplate")
    private RedisTemplate<String, String> flushRedisTemplate;*/

    @Autowired
    private void init(
            CustomMessageRepository repository,
            RedisTemplate<String, DirectMessage> template) {
        this.repository = repository;
        this.template = template;
    }
//
//    @Before
//    public void setup() throws IOException {
//        redisServer = new RedisServer();
//        redisServer.start();
//    }
//
//    @After
//    public void tearDown() {
//        redisServer.stop();
//    }

//    @AfterEach
//    private void cleanUp() {
//        flushRedisTemplate.execute((RedisCallback<Void>) connection -> {
//            connection.flushAll();
//            return null;
//        });
//    }


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
        template.opsForList().leftPushAll("create-pdf", messages);
        assertEquals("content3", template.opsForList().leftPop("create-pdf").getContent());
    }

    @Test
    void newQueueCreated() {
        DirectMessage message1 = new DirectMessage("create-pdf", "content1");
        DirectMessage message2 = new DirectMessage("create-pdf", "content2");
        DirectMessage message3 = new DirectMessage("something else", "content3");

        Long num1 = template.opsForList().leftPush(message1.getRoutingKey(), message1);
        Long num2 = template.opsForList().leftPush(message2.getRoutingKey(), message2);
        Long num3 = template.opsForList().leftPush(message3.getRoutingKey(), message3);
    }
}
