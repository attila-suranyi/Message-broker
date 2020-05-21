package com.atis.message_broker.service.exchange;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//TODO abstract class will be needed
@Service
@Component("direct")
public class DirectExchange implements EnqueueAble {

    //TODO message type
    public void enqueue(String message) {

    }
}
