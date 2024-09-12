package com.zkrallah.sanad.service.mail;

public interface MailSenderService {
    void sendEmail(String toEmail, String subject, String body);
}