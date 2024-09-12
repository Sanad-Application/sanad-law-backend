package com.zkrallah.sanad.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface StorageService {
    CompletableFuture<String> upload(MultipartFile multipartFile, Long userId);
}