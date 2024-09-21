package com.zkrallah.sanad.service.user;

import com.zkrallah.sanad.dtos.UpdateUserDto;
import com.zkrallah.sanad.entity.User;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    void addRoleToUser(String email, String roleName);

    Optional<User> getUser(String email);

    User getUserByJwt(String authHeader);

    User getUserById(Long id);

    List<User> getUsersWithRole(String roleName);

    List<User> getUsers();

    User updateUser(String authHeader, UpdateUserDto updateUserDto) throws ParseException;

    void updateUserPhoto(String authHeader, String url);

    void updateFcmToken(String email, String fcmToken);
}
