package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import com.atis.message_broker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("direct")
public class DirectExchange implements EnqueueAble {

    private Map<String, List<String>> bindings = new HashMap<>();
    /*private StudentRepository repo;

    @Autowired
    public void setRepo(StudentRepository repo) {
        this.repo = repo;
    }*/

    @Qualifier("reactiveRedisTemplateString")
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    private ReactiveListOperations<String, String> reactiveListOps;


    //TODO message type
    @Override
    public void enqueue(String bindingKey, String message) throws IncorrectRoutingKeyException {
        if (!bindings.containsKey(bindingKey)) {
            throw new IncorrectRoutingKeyException("No queue with matching routing key!");
        }
        //repository.save(message);
        bindings.get(bindingKey).add(message);
    }

    @Override
    public void registerQueue(String bindingKey) {
        this.bindings.put(bindingKey, new ArrayList<>());
    }

    @Override
    public List<String> getQueue(String bindingKey) {
        return bindings.get(bindingKey);
    }
}
