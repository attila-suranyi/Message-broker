package com.atis.message_broker.repository;

import com.atis.message_broker.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<String, String> {

}
