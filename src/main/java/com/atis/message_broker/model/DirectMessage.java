package com.atis.message_broker.model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@RedisHash("CustomMessage")
public class DirectMessage implements Serializable {

    @Generated
    private String id;

    private String routingKey;
    private String content;

    public DirectMessage(String routingKey, String content) {
        this.routingKey = routingKey;
        this.content = content;
    }
}
