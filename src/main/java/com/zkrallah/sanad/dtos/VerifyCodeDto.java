package com.zkrallah.sanad.dtos;

import lombok.Data;

@Data
public class VerifyCodeDto {
    private String email;
    private int code;
}