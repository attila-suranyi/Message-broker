package com.atis.message_broker.controller;

import com.atis.message_broker.service.exchange.EnqueueAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PublisherController {

    @Autowired
    @Qualifier("direct")
    private EnqueueAble exchange;

    @PostMapping("/direct")
    public ResponseEntity<String> directExchange(@RequestHeader("x-routing-key") String routingKey,
                                                 @RequestBody String body) {
        return ResponseEntity.ok("Message received");
    }

    // TODO route where new exchange can be created dynamically
    // Using multithreading maybe?

}
