package com.zkrallah.sanad.response;

import com.zkrallah.sanad.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresIn;
    private Date refreshTokenExpiresIn;
    private User user;
}
