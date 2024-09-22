package com.zkrallah.sanad.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String room;
    private String content;
    private Long senderId;
    private Long receiverId;
}
