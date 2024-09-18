package com.zkrallah.sanad.service.education;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkrallah.sanad.dtos.CreateEducationDto;
import com.zkrallah.sanad.entity.Education;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.EducationRepository;
import com.zkrallah.sanad.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationServiceImpl implements EducationService {

    private final UserService userService;
    private final EducationRepository educationRepository;

    @Override
    @Transactional
    public Education createEducation(String authHeader, CreateEducationDto createEducationDto) throws ParseException {
        User user = userService.getUserByJwt(authHeader);
        Lawyer lawyer = user.getLawyer();

        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer.");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date graduation = new Date(simpleDateFormat.parse(createEducationDto.getGraduation()).getTime());

        Education education = new Education();
        education.setTitle(createEducationDto.getTitle());
        education.setUniversity(createEducationDto.getUniversity());
        education.setGraduation(graduation);

        education.setLawyer(lawyer);

        return educationRepository.save(education);
    }

    @Override
    @Transactional
    public Education updateEducation(Long educationId, CreateEducationDto createEducationDto) throws ParseException {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new RuntimeException("Could not get education."));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date graduation = new Date(simpleDateFormat.parse(createEducationDto.getGraduation()).getTime());

        education.setTitle(createEducationDto.getTitle());
        education.setUniversity(createEducationDto.getUniversity());
        education.setGraduation(graduation);

        return education;
    }

    @Override
    @Transactional
    public void deleteEducation(Long educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new RuntimeException("Could not get education."));

        education.setLawyer(null);

        educationRepository.delete(education);
    }
}
