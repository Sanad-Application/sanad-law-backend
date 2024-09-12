package com.zkrallah.sanad.dtos;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String email;
    private String password;
    private int code;
}