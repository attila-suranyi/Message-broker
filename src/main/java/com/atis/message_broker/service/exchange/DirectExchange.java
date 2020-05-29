package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import com.atis.message_broker.model.DirectMessage;
import com.atis.message_broker.repository.CustomMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("direct")
public class DirectExchange implements EnqueueAble {

    private Map<String, List<String>> bindings = new HashMap<>();
    private CustomMessageRepository repository;
    private RedisTemplate<String, DirectMessage> template;

    @Autowired
    public void init(
            CustomMessageRepository repository,
            @Qualifier("defaultTemplate") RedisTemplate<String, DirectMessage> template) {
        this.repository = repository;
        this.template = template;
    }


    //TODO message type
    @Override
    public void enqueue(String bindingKey, String message) throws IncorrectRoutingKeyException {
//        if (!bindings.containsKey(bindingKey)) {
//            throw new IncorrectRoutingKeyException("No queue with matching routing key!");
//        }
        repository.save(new DirectMessage(bindingKey, message));
        //bindings.get(bindingKey).add(message);
    }

    @Override
    public void registerQueue(String bindingKey) {
        this.bindings.put(bindingKey, new ArrayList<>());
    }
}
