package com.zkrallah.sanad.controller;

import static com.zkrallah.sanad.response.ApiResponse.createFailureResponse;
import static com.zkrallah.sanad.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zkrallah.sanad.dtos.CreateEducationDto;
import com.zkrallah.sanad.dtos.CreateExperienceDto;
import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.dtos.CreateLicenseDto;
import com.zkrallah.sanad.dtos.CreateTagDto;
import com.zkrallah.sanad.entity.Education;
import com.zkrallah.sanad.entity.Experience;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.License;
import com.zkrallah.sanad.response.ApiResponse;
import com.zkrallah.sanad.response.MessageResponse;
import com.zkrallah.sanad.service.education.EducationService;
import com.zkrallah.sanad.service.experience.ExperienceService;
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
    private final EducationService educationService;
    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Lawyer>>> getLawyers() {
        try {
            List<Lawyer> lawyers = lawyerService.getLawyers();
            return ResponseEntity.ok(createSuccessResponse(lawyers));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could get lawyers: " + e.getMessage()));
        }
    }

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

    @PostMapping("/education/{userId}")
    public ResponseEntity<ApiResponse<Education>> createEducation(
            @PathVariable Long userId,
            @RequestBody CreateEducationDto createEducationDto) {
        try {
            Education education = educationService.createEducation(userId, createEducationDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(education));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to add education: " + e.getMessage()));
        }

    }

    @PostMapping("/experience/{userId}")
    public ResponseEntity<ApiResponse<Experience>> createExperience(
            @PathVariable Long userId,
            @RequestBody CreateExperienceDto createExperienceDto) {
        try {
            Experience experience = experienceService.createExperience(userId, createExperienceDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(experience));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to add experience: " + e.getMessage()));
        }

    }

    @PatchMapping("/tag/{userId}")
    public ResponseEntity<ApiResponse<MessageResponse>> addTag(
            @PathVariable Long userId,
            @RequestBody CreateTagDto createTagDto) {
        try {
            lawyerService.addTagToLawyer(userId, createTagDto.getName());
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Tag added successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not add tag: " + e.getMessage()));
        }
    }

    @PatchMapping("/untag/{userId}")
    public ResponseEntity<ApiResponse<MessageResponse>> removeTag(
            @PathVariable Long userId,
            @RequestBody CreateTagDto createTagDto) {
        try {
            lawyerService.removeTagFromLawyer(userId, createTagDto.getName());
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Tag removed successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not remove tag: " + e.getMessage()));
        }
    }

    @PutMapping("/update/license/{licenseId}")
    public ResponseEntity<ApiResponse<License>> updateLicense(
            @PathVariable Long licenseId,
            @RequestBody CreateLicenseDto createLicenseDto) {
        try {
            License license = licenseService.updateLicense(licenseId, createLicenseDto);
            return ResponseEntity.ok(createSuccessResponse(license));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update license: " + e.getMessage()));

        }
    }

    @DeleteMapping("/delete/license/{licenseId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteLicense(
            @PathVariable Long licenseId) {
        try {
            licenseService.deleteLicense(licenseId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("License deleted successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete license: " + e.getMessage()));

        }
    }
}
