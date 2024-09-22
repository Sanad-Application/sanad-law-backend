package com.zkrallah.sanad.service.messaging;

import com.zkrallah.sanad.dtos.MessageDto;

public interface FirebaseMessagingService {

    String sendNotificationByToken(String token, MessageDto message);
}
