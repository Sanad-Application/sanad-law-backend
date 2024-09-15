package com.zkrallah.sanad.service.lawyer;

import java.util.List;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.entity.Lawyer;

public interface LawyerService {
    Lawyer createLawyer(Long userId, CreateLawyerDto createLawyerDto);

    List<Lawyer> getLawyers();

    void addTagToLawyer(Long userId, String tagName);

    void removeTagFromLawyer(Long userId, String tagName);

    Lawyer updateLawyer(Long userId, CreateLawyerDto createLawyerDto);
}
