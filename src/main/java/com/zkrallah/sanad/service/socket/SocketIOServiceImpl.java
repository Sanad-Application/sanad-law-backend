package com.zkrallah.sanad.service.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.dtos.MessageDto;
import com.zkrallah.sanad.service.chat.ChatService;
import com.zkrallah.sanad.service.messaging.FirebaseMessagingService;
import com.zkrallah.sanad.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SocketIOServiceImpl implements SocketIOService{

    private final SocketIOServer server;
    @Autowired
    private final UserService userService;
    @Autowired
    private final FirebaseMessagingService firebaseMessagingService;
    @Autowired
    private final ChatService chatService;

    public SocketIOServiceImpl(SocketIOServer server, UserService userService, FirebaseMessagingService firebaseMessagingService, ChatService chatService) {
        this.server = server;
        this.userService = userService;
        this.firebaseMessagingService = firebaseMessagingService;
        this.chatService = chatService;
        init();
    }

    @Override
    public void init() {
        server.addEventListener("joinRoom", String.class, (client, room, ackSender) -> {
            client.joinRoom(room);
            server.getRoomOperations(room).sendEvent("joinedRoom", client + " Successfully joined room: " + room);
            log.info("{} joined {}", client, room);
        });

        server.addEventListener("sendMessage", MessageDto.class, (client, message, ackSender) -> {
            String room = message.getRoom();

            try {
                User receiver = userService.getUserById(message.getReceiverId());
                String token = receiver.getFirebaseToken();
                firebaseMessagingService.sendNotificationByToken(token, message);
            } catch (Exception ex) {
                log.error("Could not send notification: {}", ex.getMessage());
            }

            chatService.saveMessage(message);

            server.getRoomOperations(room).sendEvent("newMessage", message);
        });

        server.start();
    }
}
