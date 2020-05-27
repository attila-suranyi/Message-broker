package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;

import java.util.List;

//TODO interface not just for enqueue
public interface EnqueueAble {

    void enqueue(String routingKey, String message) throws IncorrectRoutingKeyException;

    void registerQueue(String bindingKey);

    List<String> getQueue(String bindingKey);
}
