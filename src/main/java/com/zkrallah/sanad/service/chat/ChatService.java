package com.zkrallah.sanad.service.chat;

import com.zkrallah.sanad.dtos.MessageDto;
import com.zkrallah.sanad.entity.Message;
import com.zkrallah.sanad.response.ChatResponse;

import java.util.List;

public interface ChatService {
    Message saveMessage(MessageDto messageDto);

    List<Message> getMessages(String room);

    List<ChatResponse> getChatsForUser(String authHeader);
}
