package com.zkrallah.sanad.service.education;

import java.text.ParseException;

import com.zkrallah.sanad.dtos.CreateEducationDto;
import com.zkrallah.sanad.entity.Education;

public interface EducationService {
    Education createEducation(String authHeader, CreateEducationDto createEducationDto) throws ParseException;

    Education updateEducation(Long educationId, CreateEducationDto createEducationDto) throws ParseException;

    void deleteEducation(Long educationId);
}
