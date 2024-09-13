package com.zkrallah.sanad.service.lawyer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.LawyerRepository;
import com.zkrallah.sanad.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LawyerServiceImpl implements LawyerService {

    private final UserService userService;
    private final LawyerRepository lawyerRepository;

    @Override
    @Transactional
    public Lawyer createLawyer(Long userId, CreateLawyerDto createLawyerDto) {
        User user = userService.getUserById(userId);

        if (user.getLawyer() != null) {
            throw new IllegalArgumentException("User already is a lawyer.");
        }

        Lawyer lawyer = new Lawyer();
        lawyer.setBio(createLawyerDto.getBio());
        lawyer.setHourlyRate(createLawyerDto.getHourlyRate());

        user.setLawyer(lawyer);
        lawyer.setUser(user);

        return lawyerRepository.save(lawyer);
    }

}
