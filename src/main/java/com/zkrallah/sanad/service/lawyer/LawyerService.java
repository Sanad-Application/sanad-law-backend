package com.zkrallah.sanad.service.lawyer;

import java.util.List;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.response.LawyersResponse;

public interface LawyerService {
    Lawyer createLawyer(String authHeader, CreateLawyerDto createLawyerDto);

    List<LawyersResponse> getLawyers();

    List<LawyersResponse> getLawyersByTag(Long tagId);

    List<LawyersResponse> getActiveLawyers();

    Lawyer getLawyer(Long lawyerId);

    void addTagToLawyer(String authHeader, Long tagId);

    void removeTagFromLawyer(String authHeader, Long tagId);

    Lawyer updateLawyer(String authHeader, CreateLawyerDto createLawyerDto);

    void toggleActivity(String authHeader);
}
