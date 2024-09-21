package com.zkrallah.sanad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String room;
    private String content;
    private Long senderId;
    private Long receiverId;
}
