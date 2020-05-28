package com.atis.message_broker;

import com.atis.message_broker.config.RedisConfig;
import com.atis.message_broker.model.DirectMessage;
import com.atis.message_broker.repository.CustomMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = RedisConfig.class)
@ComponentScan(basePackages = {"com.atis.message_broker"})
public class RedisTest {

    private CustomMessageRepository repository;

    @Autowired
    public void setRepo(CustomMessageRepository repository) {
        this.repository = repository;
    }


    @Test
    void messageRetrievable() {
        DirectMessage message = new DirectMessage("create_pdf", "This is the pdf content");
        repository.save(message);
        DirectMessage messageRetrieved = repository.findById(message.getId()).get();
        assertEquals(message, messageRetrieved);
    }
}
