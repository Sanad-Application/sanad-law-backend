package com.zkrallah.sanad.service.lawyer;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.entity.Lawyer;

import java.util.List;

public interface LawyerService {
    Lawyer createLawyer(Long userId, CreateLawyerDto createLawyerDto);

    List<Lawyer> getLawyers();

    void addTagToLawyer(Long userId, String tagName);

    void removeTagFromLawyer(Long userId, String tagName);
}
