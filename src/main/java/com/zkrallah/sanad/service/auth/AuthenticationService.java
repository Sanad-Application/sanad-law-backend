package com.zkrallah.sanad.service.auth;

import com.zkrallah.sanad.dtos.LoginUserDto;
import com.zkrallah.sanad.dtos.RegisterUserDto;
import com.zkrallah.sanad.dtos.ResetPasswordDto;
import com.zkrallah.sanad.dtos.VerifyCodeDto;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginUserDto loginUserDto);
    User signup(RegisterUserDto registerUserDto, String role);
    LoginResponse refreshToken(String authHeader);
    boolean verifyUser(VerifyCodeDto verifyCodeDto);
    void regenerateOtp(String email);
    boolean resetPassword(ResetPasswordDto resetPasswordDto);
}
