package com.zkrallah.sanad.service.chat;

import com.zkrallah.sanad.dtos.MessageDto;
import com.zkrallah.sanad.entity.Chat;
import com.zkrallah.sanad.entity.Message;
import com.zkrallah.sanad.repository.ChatRepository;
import com.zkrallah.sanad.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Message saveMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setSenderId(messageDto.getSenderId());

        Chat chat = chatRepository.findChatByRoom(messageDto.getRoom())
                .orElseGet(() -> {
                    Chat newChat = new Chat();
                    newChat.setRoom(messageDto.getRoom());
                    return chatRepository.save(newChat);
                });

        message.setChat(chat);
        message = messageRepository.save(message);
        chat.getMessages().add(message);

        return message;
    }

    @Override
    public List<Message> getMessages(String room) {
        Chat chat = chatRepository.findChatByRoom(room).orElseThrow(() -> new RuntimeException("Could not get chat."));
        return chat.getMessages();
    }

}
