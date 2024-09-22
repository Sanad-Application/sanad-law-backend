package com.zkrallah.sanad.repository;

import com.zkrallah.sanad.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findChatByRoom(String room);
}
