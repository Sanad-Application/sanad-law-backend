package com.zkrallah.sanad.service.license;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkrallah.sanad.dtos.CreateLicenseDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.License;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.LicenseRepository;
import com.zkrallah.sanad.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseServiceImpl implements LicenseService {

    private final UserService userService;
    private final LicenseRepository licenseRepository;

    @Override
    @Transactional
    public License createLicense(String authHeader, CreateLicenseDto createLicenseDto) throws ParseException {
        User user = userService.getUserByJwt(authHeader);
        Lawyer lawyer = user.getLawyer();
        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer.");
        }

        License license = lawyer.getLicense();

        if (license != null) {
            throw new IllegalArgumentException("Lawyer already has a license.");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date issuedAt = new Date(simpleDateFormat.parse(createLicenseDto.getIssuedAt()).getTime());
        Date expiresAt = new Date(simpleDateFormat.parse(createLicenseDto.getExpiresAt()).getTime());

        license = new License();
        license.setNumber(createLicenseDto.getNumber());
        license.setIssuedAt(issuedAt);
        license.setExpiresAt(expiresAt);
        license.setCountry(createLicenseDto.getCountry());

        license.setLawyer(lawyer);
        lawyer.setLicense(license);

        return licenseRepository.save(license);
    }

    @Override
    @Transactional
    public License updateLicense(Long licenseId, CreateLicenseDto createLicenseDto) throws ParseException {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new RuntimeException("Failed to get license."));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date issuedAt = new Date(simpleDateFormat.parse(createLicenseDto.getIssuedAt()).getTime());
        Date expiresAt = new Date(simpleDateFormat.parse(createLicenseDto.getExpiresAt()).getTime());

        license.setNumber(createLicenseDto.getNumber());
        license.setIssuedAt(issuedAt);
        license.setExpiresAt(expiresAt);
        license.setCountry(createLicenseDto.getCountry());

        return license;
    }

    @Override
    @Transactional
    public void deleteLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new RuntimeException("Failed to get license"));

        Lawyer lawyer = license.getLawyer();
        if (lawyer != null) {
            lawyer.setLicense(null);
        }

        licenseRepository.delete(license);
    }
}
