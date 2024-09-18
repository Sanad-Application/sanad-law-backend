package com.zkrallah.sanad.controller;

import static com.zkrallah.sanad.response.ApiResponse.createFailureResponse;
import static com.zkrallah.sanad.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zkrallah.sanad.dtos.CreateEducationDto;
import com.zkrallah.sanad.dtos.CreateExperienceDto;
import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.dtos.CreateLicenseDto;
import com.zkrallah.sanad.dtos.CreateTagDto;
import com.zkrallah.sanad.entity.Education;
import com.zkrallah.sanad.entity.Experience;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.License;
import com.zkrallah.sanad.entity.Request;
import com.zkrallah.sanad.response.ApiResponse;
import com.zkrallah.sanad.response.MessageResponse;
import com.zkrallah.sanad.service.education.EducationService;
import com.zkrallah.sanad.service.experience.ExperienceService;
import com.zkrallah.sanad.service.lawyer.LawyerService;
import com.zkrallah.sanad.service.license.LicenseService;
import com.zkrallah.sanad.service.request.RequestService;

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
    private final RequestService requestService;

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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Lawyer>> createLawyer(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateLawyerDto createLawyerDto) {
        try {
            Lawyer lawyer = lawyerService.createLawyer(authHeader, createLawyerDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(lawyer));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to create lawyer: " + e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Lawyer>> updateLawyer(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateLawyerDto createLawyerDto) {
        try {
            Lawyer lawyer = lawyerService.updateLawyer(authHeader, createLawyerDto);
            return ResponseEntity.ok(createSuccessResponse(lawyer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not update lawyer: " + e));
        }
    }

    @PostMapping("/license")
    public ResponseEntity<ApiResponse<License>> createLicense(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateLicenseDto createLicenseDto) {
        try {
            License license = licenseService.createLicense(authHeader, createLicenseDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(license));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to create license: " + e.getMessage()));
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

    @PostMapping("/education")
    public ResponseEntity<ApiResponse<Education>> createEducation(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateEducationDto createEducationDto) {
        try {
            Education education = educationService.createEducation(authHeader, createEducationDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(education));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to add education: " + e.getMessage()));
        }
    }

    @PutMapping("/update/education/{educationId}")
    public ResponseEntity<ApiResponse<Education>> updateEducation(
            @PathVariable Long educationId,
            @RequestBody CreateEducationDto createEducationDto) {
        try {
            Education education = educationService.updateEducation(educationId, createEducationDto);
            return ResponseEntity.ok(createSuccessResponse(education));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update education: " + e.getMessage()));

        }
    }

    @DeleteMapping("/delete/education/{educationId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteEducation(
            @PathVariable Long educationId) {
        try {
            educationService.deleteEducation(educationId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Education deleted successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete education: " + e.getMessage()));

        }
    }

    @PostMapping("/experience")
    public ResponseEntity<ApiResponse<Experience>> createExperience(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateExperienceDto createExperienceDto) {
        try {
            Experience experience = experienceService.createExperience(authHeader, createExperienceDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(experience));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to add experience: " + e.getMessage()));
        }
    }

    @PutMapping("/update/experience/{experienceId}")
    public ResponseEntity<ApiResponse<Experience>> updateExperience(
            @PathVariable Long experienceId,
            @RequestBody CreateExperienceDto createExperienceDto) {
        try {
            Experience experience = experienceService.updateExperience(experienceId, createExperienceDto);
            return ResponseEntity.ok(createSuccessResponse(experience));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update experience: " + e.getMessage()));

        }
    }

    @DeleteMapping("/delete/experience/{experienceId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteExperience(
            @PathVariable Long experienceId) {
        try {
            experienceService.deleteExperience(experienceId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Experience deleted successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete experience: " + e.getMessage()));

        }
    }

    @PatchMapping("/tag")
    public ResponseEntity<ApiResponse<MessageResponse>> addTag(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateTagDto createTagDto) {
        try {
            lawyerService.addTagToLawyer(authHeader, createTagDto.getName());
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Tag added successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not add tag: " + e.getMessage()));
        }
    }

    @PatchMapping("/untag")
    public ResponseEntity<ApiResponse<MessageResponse>> removeTag(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateTagDto createTagDto) {
        try {
            lawyerService.removeTagFromLawyer(authHeader, createTagDto.getName());
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Tag removed successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not remove tag: " + e.getMessage()));
        }
    }

    @PatchMapping("/toggle")
    public ResponseEntity<ApiResponse<MessageResponse>> toggleActivity(
            @RequestHeader("Authorization") String authHeader) {
        try {
            lawyerService.toggleActivity(authHeader);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Status toggled succesfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not toggle: " + e.getMessage()));
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<Request>>> getClientRequests(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam int type) {
        try {
            List<Request> requests = requestService.getClientRequests(authHeader, type);
            return ResponseEntity.ok(createSuccessResponse(requests));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get requests: " + e.getMessage()));
        }
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<ApiResponse<Request>> getRequest(
            @PathVariable Long requestId) {
        try {
            Request request = requestService.getRequest(requestId);
            return ResponseEntity.ok(createSuccessResponse(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createFailureResponse("Could not get request: " + e.getMessage()));
        }
    }

    @PatchMapping("/request")
    public ResponseEntity<ApiResponse<Request>> updateRequestStatus(
            @RequestParam Long requestId,
            @RequestParam int type) {
        try {
            Request request = requestService.updateRequestStatus(requestId, type);
            return ResponseEntity.ok(createSuccessResponse(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update request status: " + e.getMessage()));
        }
    }
}
