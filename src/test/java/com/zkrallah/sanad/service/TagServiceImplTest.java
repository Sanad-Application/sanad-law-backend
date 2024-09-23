package com.zkrallah.sanad.service;

import com.zkrallah.sanad.dtos.CreateTagDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.Tag;
import com.zkrallah.sanad.repository.TagRepository;
import com.zkrallah.sanad.service.tag.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void createTag_shouldThrowExceptionIfTagAlreadyExists() {
        // Given
        CreateTagDto createTagDto = new CreateTagDto();
        createTagDto.setName("Java");
        when(tagRepository.findByName("Java")).thenReturn(Optional.of(new Tag()));

        // When and Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tagService.createTag(createTagDto);
        });

        assertEquals("Tag with the name 'Java' already exists.", exception.getMessage());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    void createTag_shouldSaveNewTag() {
        // Given
        CreateTagDto createTagDto = new CreateTagDto();
        createTagDto.setName("Java");
        when(tagRepository.findByName("Java")).thenReturn(Optional.empty());
        Tag savedTag = new Tag();
        savedTag.setName("Java");
        when(tagRepository.save(any(Tag.class))).thenReturn(savedTag);

        // When
        Tag result = tagService.createTag(createTagDto);

        // Then
        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void getTags_shouldReturnListOfTags() {
        // Given
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Java", new ArrayList<>(), new ArrayList<>()));
        tags.add(new Tag(2L, "Python", new ArrayList<>(), new ArrayList<>()));
        when(tagRepository.findAll()).thenReturn(tags);

        // When
        List<Tag> result = tagService.getTags();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void deleteTag_shouldDeleteTagIfExists() {
        // Given
        Tag tag = new Tag(1L, "Java", new ArrayList<>(), new ArrayList<>());
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        // When
        tagService.deleteTag(1L);

        // Then
        verify(tagRepository, times(1)).delete(tag);
    }

    @Test
    void deleteTag_shouldThrowExceptionIfTagNotFound() {
        // Given
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tagService.deleteTag(1L);
        });

        assertEquals("Tag not found.", exception.getMessage());
        verify(tagRepository, never()).delete(any(Tag.class));
    }

    @Test
    void getTagByName_shouldReturnTagIfFound() {
        // Given
        Tag tag = new Tag(1L, "Java", new ArrayList<>(), new ArrayList<>());
        when(tagRepository.findByName("Java")).thenReturn(Optional.of(tag));

        // When
        Tag result = tagService.getTagByName("Java");

        // Then
        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(tagRepository, times(1)).findByName("Java");
    }

    @Test
    void getTagByName_shouldThrowExceptionIfTagNotFound() {
        // Given
        when(tagRepository.findByName("Java")).thenReturn(Optional.empty());

        // When and Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tagService.getTagByName("Java");
        });

        assertEquals("Tag not found.", exception.getMessage());
        verify(tagRepository, times(1)).findByName("Java");
    }

    @Test
    void getTagById_shouldReturnTagIfFound() {
        // Given
        Tag tag = new Tag(1L, "Java", new ArrayList<>(), new ArrayList<>());
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        // When
        Tag result = tagService.getTagById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    void getTagById_shouldThrowExceptionIfTagNotFound() {
        // Given
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tagService.getTagById(1L);
        });

        assertEquals("Tag not foumd.", exception.getMessage());
        verify(tagRepository, times(1)).findById(1L);
    }
}
