package com.zkrallah.sanad.service.experience;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkrallah.sanad.dtos.CreateExperienceDto;
import com.zkrallah.sanad.entity.Experience;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.ExperienceRepository;
import com.zkrallah.sanad.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {

    private final UserService userService;
    private final ExperienceRepository experienceRepository;

    @Override
    @Transactional
    public Experience createExperience(Long userId, CreateExperienceDto createExperienceDto) throws ParseException {
        User user = userService.getUserById(userId);
        Lawyer lawyer = user.getLawyer();

        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer.");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = new Date(simpleDateFormat.parse(createExperienceDto.getStartDate()).getTime());
        Date endDate = new Date(simpleDateFormat.parse(createExperienceDto.getEndDate()).getTime());

        Experience experience = new Experience();
        experience.setTitle(createExperienceDto.getTitle());
        experience.setCompany(createExperienceDto.getCompany());
        experience.setLocation(createExperienceDto.getLocation());
        experience.setDescription(createExperienceDto.getDescription());
        experience.setStartDate(startDate);
        experience.setEndDate(endDate);

        experience.setLawyer(lawyer);

        return experienceRepository.save(experience);
    }

}
