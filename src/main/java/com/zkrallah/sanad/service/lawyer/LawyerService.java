package com.zkrallah.sanad.service.lawyer;

import java.util.List;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.entity.Lawyer;

public interface LawyerService {
    Lawyer createLawyer(String authHeader, CreateLawyerDto createLawyerDto);

    List<Lawyer> getLawyers();

    List<Lawyer> getLawyersByTag(Long tagId);

    List<Lawyer> getActiveLawyers();

    Lawyer getLawyer(Long lawyerId);

    void addTagToLawyer(String authHeader, String tagName);

    void removeTagFromLawyer(String authHeader, String tagName);

    Lawyer updateLawyer(String authHeader, CreateLawyerDto createLawyerDto);

    void toggleActivity(String authHeader);
}
