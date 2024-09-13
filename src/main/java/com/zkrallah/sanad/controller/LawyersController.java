package com.zkrallah.sanad.controller;

import static com.zkrallah.sanad.response.ApiResponse.createFailureResponse;
import static com.zkrallah.sanad.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.dtos.CreateLicenseDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.License;
import com.zkrallah.sanad.response.ApiResponse;
import com.zkrallah.sanad.service.lawyer.LawyerService;
import com.zkrallah.sanad.service.license.LicenseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/lawyers")
@RequiredArgsConstructor
@Slf4j
public class LawyersController {

    private final LawyerService lawyerService;
    private final LicenseService licenseService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse<Lawyer>> createLawyer(
            @PathVariable Long userId,
            @RequestBody CreateLawyerDto createLawyerDto) {
        try {
            Lawyer lawyer = lawyerService.createLawyer(userId, createLawyerDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(lawyer));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to create lawyer: " + e.getMessage()));
        }
    }

    @PostMapping("/license/{userId}")
    public ResponseEntity<ApiResponse<License>> createLicense(
            @PathVariable Long userId,
            @RequestBody CreateLicenseDto createLicenseDto) {
        try {
            License license = licenseService.createLicense(userId, createLicenseDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(license));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to create license: " + e.getMessage()));
        }

    }
}
