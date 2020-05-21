package com.atis.message_broker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PublisherController {

    @PostMapping("/direct_exchange")
    public ResponseEntity<String> directExchange(@RequestHeader("x-routing-key") String routingKey) {
        return ResponseEntity.ok("Message received");
    }

}
