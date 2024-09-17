package com.zkrallah.sanad.controller;

import static com.zkrallah.sanad.response.ApiResponse.createFailureResponse;
import static com.zkrallah.sanad.response.ApiResponse.createSuccessResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.response.ApiResponse;
import com.zkrallah.sanad.service.lawyer.LawyerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientsController {

    private final LawyerService lawyerService;

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
        @PathVariable Long lawyerId
    ) {
        try {
            Lawyer lawyer = lawyerService.getLawyer(lawyerId);
            return ResponseEntity.ok(createSuccessResponse(lawyer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get lawyer: " + e.getMessage()));
        }
    }

}
