package com.zkrallah.sanad.dtos;

import lombok.Data;

@Data
public class CreateLawyerDto {
    private String bio;
    private Double hourlyRate;
    private String location;
    private int experienceYears;
}
