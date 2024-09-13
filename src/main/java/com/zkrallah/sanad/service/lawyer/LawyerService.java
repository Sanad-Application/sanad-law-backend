package com.zkrallah.sanad.service.lawyer;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.entity.Lawyer;

public interface LawyerService {
    Lawyer createLawyer(Long userId, CreateLawyerDto createLawyerDto);
}
