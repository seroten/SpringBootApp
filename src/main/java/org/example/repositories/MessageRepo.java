package org.example.repositories;

import org.example.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long>, JpaRepository<Message, Long> {
    List<Message> findByTag(String tag);
    void deleteMessagesById(int id);
}
