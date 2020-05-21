package com.atis.message_broker.exception;

public class IncorrectRoutingKeyException extends Exception {

    public IncorrectRoutingKeyException(String message) {
        super(message);
    }
}
