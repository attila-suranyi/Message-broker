package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO abstract class will be needed
//TODO make exchange dynamically creatable
@Service("direct")
public class DirectExchange implements EnqueueAble {

    //TODO Java object serialization, Redis
    //TODO Enum for bindings?
    private Map<String, List<String>> bindings = new HashMap<>();


    //TODO message type
    @Override
    public void enqueue(String bindingKey, String message) throws IncorrectRoutingKeyException {
        if (!bindings.containsKey(bindingKey)) {
            throw new IncorrectRoutingKeyException("No queue with matching routing key!");
        }
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
