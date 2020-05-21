package com.atis.message_broker.service.exchange;

public interface EnqueueAble {

    void enqueue(String message);
}
