package com.zkrallah.sanad.service.lawyer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkrallah.sanad.dtos.CreateLawyerDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.Tag;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.LawyerRepository;
import com.zkrallah.sanad.service.tag.TagService;
import com.zkrallah.sanad.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LawyerServiceImpl implements LawyerService {

    private final UserService userService;
    private final TagService tagService;
    private final LawyerRepository lawyerRepository;

    @Override
    @Transactional
    public Lawyer createLawyer(String authHeader, CreateLawyerDto createLawyerDto) {
        User user = userService.getUserByJwt(authHeader);

        if (user.getLawyer() != null) {
            throw new IllegalArgumentException("User already is a lawyer.");
        }

        Lawyer lawyer = new Lawyer();
        lawyer.setBio(createLawyerDto.getBio());
        lawyer.setHourlyRate(createLawyerDto.getHourlyRate());

        user.setLawyer(lawyer);
        lawyer.setUser(user);

        return lawyerRepository.save(lawyer);
    }

    @Override
    public List<Lawyer> getLawyers() {
        return lawyerRepository.findAll();
    }

    @Override
    @Transactional
    public void addTagToLawyer(String authHeader, String tagName) {
        User user = userService.getUserByJwt(authHeader);
        Lawyer lawyer = user.getLawyer();

        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer.");
        }

        Tag tag = tagService.getTagByName(tagName);
        if (lawyer.getTags().contains(tag)) {
            throw new IllegalArgumentException("Tag already exists.");
        }

        lawyer.getTags().add(tag);
    }

    @Override
    @Transactional
    public void removeTagFromLawyer(String authHeader, String tagName) {
        User user = userService.getUserByJwt(authHeader);
        Lawyer lawyer = user.getLawyer();

        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer.");
        }

        Tag tag = tagService.getTagByName(tagName);
        if (!lawyer.getTags().contains(tag)) {
            throw new IllegalArgumentException("Tag already doesn't exist.");
        }

        lawyer.getTags().remove(tag);
    }

    @Override
    @Transactional
    public Lawyer updateLawyer(String authHeader, CreateLawyerDto createLawyerDto) {
        User user = userService.getUserByJwt(authHeader);
        Lawyer lawyer = user.getLawyer();

        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer.");
        }

        lawyer.setBio(createLawyerDto.getBio());
        lawyer.setHourlyRate(createLawyerDto.getHourlyRate());

        return lawyer;
    }

    @Override
    public List<Lawyer> getLawyersByTag(Long tagId) {
        Tag tag = tagService.getTagById(tagId);

        return tag.getLawyers();
    }

    @Override
    public Lawyer getLawyer(Long lawyerId) {
        return lawyerRepository.findById(lawyerId).orElseThrow(() -> new RuntimeException("Could not get lawyer."));
    }

    @Override
    @Transactional
    public void toggleActivity(String authHeader) {
        User user = userService.getUserByJwt(authHeader);
        Lawyer lawyer = user.getLawyer();

        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer");
        }

        boolean isActive = lawyer.isActive();
        lawyer.setActive(!isActive);
    }

    @Override
    public List<Lawyer> getActiveLawyers() {
        return lawyerRepository.findByIsActiveTrue()
                .orElseThrow(() -> new RuntimeException("Could not get lawyers."));
    }
}
