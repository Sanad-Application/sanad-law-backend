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

        return response;
    }
}
