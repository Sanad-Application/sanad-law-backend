package com.zkrallah.sanad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkrallah.sanad.entity.Lawyer;

public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
}
