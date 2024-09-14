package com.zkrallah.sanad.service.tag;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkrallah.sanad.dtos.CreateTagDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.Tag;
import com.zkrallah.sanad.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag createTag(CreateTagDto createTagDto) {
        tagRepository.findByName(createTagDto.getName())
                .ifPresent(existingTag -> {
                    throw new IllegalArgumentException(
                            "Tag with the name '" + createTagDto.getName() + "' already exists.");
                });

        Tag tag = new Tag();
        tag.setName(createTagDto.getName());

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found."));

        List<Lawyer> lawyersWithTag = tag.getLawyers();
        for (Lawyer lawyer : lawyersWithTag) {
            lawyer.getTags().remove(tag);
        }

        tagRepository.delete(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name).orElseThrow(() -> new RuntimeException("Tag not found."));
    }

}
