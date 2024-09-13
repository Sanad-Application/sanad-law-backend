package com.zkrallah.sanad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkrallah.sanad.entity.License;

public interface LicenseRepository extends JpaRepository<License, Long> {
}
