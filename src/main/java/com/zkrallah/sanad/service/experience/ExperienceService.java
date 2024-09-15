package com.zkrallah.sanad.service.experience;

import java.text.ParseException;

import com.zkrallah.sanad.dtos.CreateExperienceDto;
import com.zkrallah.sanad.entity.Experience;

public interface ExperienceService {
    Experience createExperience(Long userId, CreateExperienceDto createExperienceDto) throws ParseException;

    Experience updateExperience(Long experienceId, CreateExperienceDto createExperienceDto) throws ParseException;

    void deleteExperience(Long experienceId);
}
