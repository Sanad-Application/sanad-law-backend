package com.zkrallah.sanad.repository;

import com.zkrallah.sanad.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
