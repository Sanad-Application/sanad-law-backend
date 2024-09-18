package com.zkrallah.sanad.service.storage;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    CompletableFuture<String> upload(MultipartFile multipartFile, String authHeader);

    CompletableFuture<String> upload(MultipartFile multipartFile);
}
