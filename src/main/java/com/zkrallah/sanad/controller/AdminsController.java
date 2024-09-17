package com.zkrallah.sanad.controller;

import static com.zkrallah.sanad.response.ApiResponse.createFailureResponse;
import static com.zkrallah.sanad.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zkrallah.sanad.dtos.CreateTagDto;
import com.zkrallah.sanad.entity.Tag;
import com.zkrallah.sanad.response.ApiResponse;
import com.zkrallah.sanad.response.MessageResponse;
import com.zkrallah.sanad.service.tag.TagService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Slf4j
public class AdminsController {

    private final TagService tagService;

    @PostMapping("/create/tag")
    public ResponseEntity<ApiResponse<Tag>> createTag(@RequestBody CreateTagDto createTagDto) {
        try {
            Tag tag = tagService.createTag(createTagDto);
            return ResponseEntity.status(CREATED).body(createSuccessResponse(tag));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to create tag: " + e.getMessage()));
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<ApiResponse<List<Tag>>> getTags() {
        try {
            List<Tag> tags = tagService.getTags();
            return ResponseEntity.ok(createSuccessResponse(tags));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get tags: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/tag/{tagId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteTag(
            @PathVariable Long tagId) {
        try {
            tagService.deleteTag(tagId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Tag deleted successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete tag: " + e.getMessage()));
        }
    }
}
