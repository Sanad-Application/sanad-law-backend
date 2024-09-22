package com.zkrallah.sanad.service.chat;

import com.zkrallah.sanad.dtos.MessageDto;
import com.zkrallah.sanad.entity.Chat;
import com.zkrallah.sanad.entity.Message;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.ChatRepository;
import com.zkrallah.sanad.repository.MessageRepository;
import com.zkrallah.sanad.response.ChatResponse;
import com.zkrallah.sanad.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

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
                    newChat.setUser1Id(messageDto.getSenderId());
                    newChat.setUser2Id(messageDto.getReceiverId());

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

    @Override
    public List<ChatResponse> getChatsForUser(String authHeader) {
        User user = userService.getUserByJwt(authHeader);
        Long userId = user.getId();

        List<Chat> chats = chatRepository.findAllByUserId(userId);
        List<ChatResponse> chatResponses = new ArrayList<>();

        for (Chat chat : chats) {
            Long otherUserId = chat.getUser1Id().equals(userId) ? chat.getUser2Id() : chat.getUser1Id();
            User otherUser = userService.getUserById(otherUserId);
            String otherUserName = otherUser.getFirstName() + otherUser.getLastName();
            chatResponses.add(new ChatResponse(chat, otherUserName));
        }

        return chatResponses;
    }

}
