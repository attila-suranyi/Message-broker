package com.atis.message_broker.controller;

import com.atis.message_broker.exception.IncorrectRoutingKeyException;
import com.atis.message_broker.service.exchange.EnqueueAble;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class PublisherController {

    @Qualifier("direct")
    private EnqueueAble exchange;


    @PostMapping("/direct")
    public ResponseEntity<String> directExchange(
            @RequestHeader("x-routing-key") String routingKey,
            @RequestBody String message) {
        try {
            exchange.enqueue(routingKey, message);
        } catch (IncorrectRoutingKeyException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Message received");
    }

    @PostMapping("/direct/register")
    public ResponseEntity<String> registerDirectQueue(
            @RequestHeader("x-routing-key") String routingKey) {
        exchange.registerQueue(routingKey);
        return ResponseEntity.ok().body("Queue registered");
    }
}
