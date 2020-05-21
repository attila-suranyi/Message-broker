package com.atis.message_broker.service.exchange;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;

import java.net.http.HttpRequest;

public interface EnqueueAble {

    void enqueue(String routingKey, String message) throws IncorrectRoutingKeyException;
}
