package com.zkrallah.sanad.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateRequestDto {
    private Long id;
    private String type;
    private Long tagId;
    private String title;
    private String description;
    private String attachment;
    private List<String> keywords;
}
