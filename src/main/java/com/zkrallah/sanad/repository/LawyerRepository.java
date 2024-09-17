package com.zkrallah.sanad.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkrallah.sanad.entity.Lawyer;

public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
    Optional<List<Lawyer>> findByIsActiveTrue();
}
