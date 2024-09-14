package com.zkrallah.sanad.service.tag;

import java.util.List;

import com.zkrallah.sanad.dtos.CreateTagDto;
import com.zkrallah.sanad.entity.Tag;

public interface TagService {
    Tag createTag(CreateTagDto createTagDto);

    List<Tag> getTags();

    Tag getTagByName(String name);

    void deleteTag(String tagName);
}
