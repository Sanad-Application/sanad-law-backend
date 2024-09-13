package com.zkrallah.sanad.dtos;

import lombok.Data;

@Data
public class CreateExperienceDto {
    private String title;
    private String company;
    private String location;
    private String description;
    private String startDate;
    private String endDate;
}
