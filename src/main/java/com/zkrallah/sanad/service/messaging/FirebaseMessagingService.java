package com.zkrallah.sanad.service.messaging;

import com.zkrallah.sanad.model.Message;

public interface FirebaseMessagingService {

    String sendNotificationByToken(String token, Message message);
}
