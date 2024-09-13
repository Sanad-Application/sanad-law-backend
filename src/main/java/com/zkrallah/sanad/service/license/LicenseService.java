package com.zkrallah.sanad.service.license;

import java.text.ParseException;

import com.zkrallah.sanad.dtos.CreateLicenseDto;
import com.zkrallah.sanad.entity.License;

public interface LicenseService {
    License createLicense(Long userId, CreateLicenseDto createLicenseDto) throws ParseException;
}
