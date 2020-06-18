package com.atis.message_broker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Indexed;

import java.io.Serializable;
import java.util.UUID;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode
@RedisHash("CustomMessage")
public class DirectMessage implements Serializable {

    //TODO id generation
    private UUID id;

    private String routingKey;
    private String content;

    public DirectMessage(String routingKey, String content) {
        this.routingKey = routingKey;
        this.content = content;
        this.id = UUID.randomUUID();
    }
}
