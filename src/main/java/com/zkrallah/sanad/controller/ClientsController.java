package com.zkrallah.sanad.controller;

import static com.zkrallah.sanad.response.ApiResponse.createFailureResponse;
import static com.zkrallah.sanad.response.ApiResponse.createSuccessResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zkrallah.sanad.dtos.CreateRequestDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.Request;
import com.zkrallah.sanad.response.ApiResponse;
import com.zkrallah.sanad.response.MessageResponse;
import com.zkrallah.sanad.service.lawyer.LawyerService;
import com.zkrallah.sanad.service.request.RequestService;
import com.zkrallah.sanad.service.storage.StorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientsController {

    private final LawyerService lawyerService;
    private final StorageService storageService;
    private final RequestService requestService;

    @GetMapping("/lawyers")
    public ResponseEntity<ApiResponse<List<Lawyer>>> getLawyers() {
        try {
            List<Lawyer> lawyers = lawyerService.getLawyers();
            return ResponseEntity.ok(createSuccessResponse(lawyers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get lawyers: " + e.getMessage()));
        }
    }

    @GetMapping("/lawyers/tag/{tagId}")
    public ResponseEntity<ApiResponse<List<Lawyer>>> getLawyersByTag(
            @PathVariable Long tagId) {
        try {
            List<Lawyer> lawyers = lawyerService.getLawyersByTag(tagId);
            return ResponseEntity.ok(createSuccessResponse(lawyers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get lawyers: " + e.getMessage()));
        }
    }

    @GetMapping("lawyers/active")
    public ResponseEntity<ApiResponse<List<Lawyer>>> getActiveLawyers() {
        try {
            List<Lawyer> lawyers = lawyerService.getActiveLawyers();
            return ResponseEntity.ok(createSuccessResponse(lawyers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get lawyers: " + e.getMessage()));
        }
    }

    @GetMapping("/lawyer/{lawyerId}")
    public ResponseEntity<ApiResponse<Lawyer>> getLawyer(
            @PathVariable Long lawyerId) {
        try {
            Lawyer lawyer = lawyerService.getLawyer(lawyerId);
            return ResponseEntity.ok(createSuccessResponse(lawyer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get lawyer: " + e.getMessage()));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<MessageResponse>> upload(
            @RequestParam("file") MultipartFile multipartFile) {
        try {
            log.info("Receiving request on {}", Thread.currentThread().getName());
            String url = storageService.upload(multipartFile).get();
            log.info("Responding on {}", Thread.currentThread().getName());

            return ResponseEntity.ok(createSuccessResponse(new MessageResponse(url)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not upload user's file: " + e.getMessage()));
        }
    }

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<Request>> createRequest(
            @RequestParam() Long userId,
            @RequestParam() Long lawyerId,
            @RequestBody CreateRequestDto createRequestDto) {
        try {
            Request request = requestService.createRequest(userId, lawyerId, createRequestDto);
            return ResponseEntity.ok(createSuccessResponse(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not create request: " + e.getMessage()));
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<Request>>> getRequests(
            @RequestParam Long userId,
            @RequestParam int type) {
        try {
            List<Request> requests = requestService.getRequests(userId, type);
            return ResponseEntity.ok(createSuccessResponse(requests));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get requests: " + e.getMessage()));
        }
    }

}
