package com.zkrallah.sanad.response;

import com.zkrallah.sanad.entity.Chat;
import lombok.Data;

@Data
public class ChatResponse {
    private Long chatId;
    private String room;
    private String otherUserName;

    public ChatResponse(Chat chat, String otherUserName) {
        this.chatId = chat.getId();
        this.room = chat.getRoom();
        this.otherUserName = otherUserName;
    }
}
