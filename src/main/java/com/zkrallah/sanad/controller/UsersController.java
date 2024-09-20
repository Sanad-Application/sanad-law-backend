package com.zkrallah.sanad.controller;

import static com.zkrallah.sanad.response.ApiResponse.createFailureResponse;
import static com.zkrallah.sanad.response.ApiResponse.createSuccessResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zkrallah.sanad.dtos.UpdateUserDto;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.response.ApiResponse;
import com.zkrallah.sanad.response.MessageResponse;
import com.zkrallah.sanad.service.storage.StorageService;
import com.zkrallah.sanad.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService userService;
    private final StorageService storageService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getMe(
            @RequestHeader("Authorization") String authHeader) {
        try {
            User user = userService.getUserByJwt(authHeader);
            return ResponseEntity.ok(createSuccessResponse(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get user: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
        return ResponseEntity.ok(createSuccessResponse(userService.getUsers()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(createSuccessResponse(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not fetch user: " + e.getMessage()));
        }
    }

    @PatchMapping("/update-user")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UpdateUserDto updateUser) {
        try {
            User updatedUser = userService.updateUser(authHeader, updateUser);
            return ResponseEntity.ok(createSuccessResponse(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update user: " + e.getMessage()));
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<ApiResponse<MessageResponse>> upload(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String url = storageService.upload(multipartFile, authHeader).get();
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse(url)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not upload user's image: " + e.getMessage()));
        }
    }
}
