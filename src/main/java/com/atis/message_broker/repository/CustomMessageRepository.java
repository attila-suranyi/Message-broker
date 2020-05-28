package com.atis.message_broker.repository;

import com.atis.message_broker.model.DirectMessage;
import org.springframework.data.repository.CrudRepository;

public interface CustomMessageRepository extends CrudRepository<DirectMessage, String> {
}
