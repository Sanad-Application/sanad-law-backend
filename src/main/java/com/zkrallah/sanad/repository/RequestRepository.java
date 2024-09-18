package com.zkrallah.sanad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkrallah.sanad.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
