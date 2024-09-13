package com.zkrallah.sanad.dtos;

import lombok.Data;

@Data
public class CreateLicenseDto {
    private int number;
    private String issuedAt;
    private String expiresAt;
    private String country;
}
