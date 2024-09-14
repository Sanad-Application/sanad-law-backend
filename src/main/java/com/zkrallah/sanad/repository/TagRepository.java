package com.zkrallah.sanad.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkrallah.sanad.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
