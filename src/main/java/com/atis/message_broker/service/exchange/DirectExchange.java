package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//TODO abstract class will be needed
//TODO make exchange dynamically creatable
@Component("direct")
public class DirectExchange implements EnqueueAble {

    //TODO use DB or something
    private Map<String, String> bindings = new HashMap<>();


    //TODO message type
    @Override
    public void enqueue(String routingKey, String message) throws IncorrectRoutingKeyException {
        if (!bindings.containsKey(routingKey)) {
            throw new IncorrectRoutingKeyException("");
        }
    }

    @Override
    public void registerQueue(String queue, String bindingKey) {
        this.bindings.put(bindingKey, queue);
    }
}
