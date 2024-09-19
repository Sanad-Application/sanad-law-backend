package com.zkrallah.sanad.response;

import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.User;
import lombok.Data;

@Data
public class LawyersResponse {
    private Long id;
    private String bio;
    private double hourlyRate;
    private boolean isActive;
    private User user;
    private String location;
    private int experienceYears;
    private double avgRate;

    public static LawyersResponse toLawyersResponse(Lawyer lawyer) {
        if (lawyer == null) {
            throw new RuntimeException("Could not map lawyer");
        }

        LawyersResponse response = new LawyersResponse();
        response.setId(lawyer.getId());
        response.setBio(lawyer.getBio());
        response.setHourlyRate(lawyer.getHourlyRate());
        response.setActive(lawyer.isActive());
        response.setUser(lawyer.getUser());
        response.setExperienceYears(lawyer.getExperienceYears());
        response.setLocation(lawyer.getLocation());
        response.setAvgRate(lawyer.getAvgRate());

        return response;
    }
}
