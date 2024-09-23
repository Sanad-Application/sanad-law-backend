package com.zkrallah.sanad.service;

import com.zkrallah.sanad.dtos.CreateExperienceDto;
import com.zkrallah.sanad.entity.Experience;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.ExperienceRepository;
import com.zkrallah.sanad.service.experience.ExperienceServiceImpl;
import com.zkrallah.sanad.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExperienceServiceImplTest {

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    @Mock
    private UserService userService;

    @Mock
    private ExperienceRepository experienceRepository;

    private CreateExperienceDto createExperienceDto;
    private User mockUser;
    private Lawyer mockLawyer;
    private Experience mockExperience;

    @BeforeEach
    public void setup() {
        // Set up CreateExperienceDto with dummy data
        createExperienceDto = new CreateExperienceDto();
        createExperienceDto.setTitle("Junior Lawyer");
        createExperienceDto.setCompany("Better Call Saul");
        createExperienceDto.setDescription("You better call Saul!");
        createExperienceDto.setStartDate("2020-02-22");
        createExperienceDto.setEndDate("2022-02-22");

        // Mock Lawyer and User
        mockLawyer = new Lawyer();
        mockUser = new User();
        mockUser.setLawyer(mockLawyer);

        // Mock Experience object
        mockExperience = new Experience();
        mockExperience.setId(1L);
        mockExperience.setTitle("Senior Lawyer");
        mockExperience.setCompany("Breaking Bad");
        mockExperience.setLawyer(mockLawyer);
    }

    @Test
    public void testCreateExperience_Success() throws ParseException {
        // Arrange: Mock user service to return the mock user
        when(userService.getUserByJwt(anyString())).thenReturn(mockUser);
        when(experienceRepository.save(any(Experience.class))).thenReturn(mockExperience);

        // Act: Call the service method
        Experience createdExperience = experienceService.createExperience("Bearer some-jwt", createExperienceDto);

        // Assert: Verify the experience was saved and properties are correct
        assertNotNull(createdExperience);
        assertEquals(mockExperience.getTitle(), createdExperience.getTitle());
        assertEquals(mockExperience.getCompany(), createdExperience.getCompany());

        // Verify interactions with mocks
        verify(userService).getUserByJwt(anyString());
        verify(experienceRepository).save(any(Experience.class));
    }

    @Test
    public void testCreateExperience_NotALawyer() {
        // Arrange: Set user without a lawyer and mock
        mockUser.setLawyer(null);
        when(userService.getUserByJwt(anyString())).thenReturn(mockUser);

        // Act & Assert: Verify the exception is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            experienceService.createExperience("Bearer some-jwt", createExperienceDto);
        });

        assertEquals("User is not a lawyer.", exception.getMessage());
        verify(userService).getUserByJwt(anyString());
        verifyNoInteractions(experienceRepository);
    }

    @Test
    public void testUpdateExperience_Success() throws ParseException {
        // Arrange: Mock repository to return existing experience
        when(experienceRepository.findById(anyLong())).thenReturn(Optional.of(mockExperience));

        // Act: Call the service method
        Experience updatedExperience = experienceService.updateExperience(1L, createExperienceDto);

        // Assert: Verify properties are updated correctly
        assertNotNull(updatedExperience);
        assertEquals(createExperienceDto.getTitle(), updatedExperience.getTitle());
        assertEquals(createExperienceDto.getCompany(), updatedExperience.getCompany());

        // Verify interactions
        verify(experienceRepository).findById(anyLong());
    }

    @Test
    public void testUpdateExperience_NotFound() throws ParseException {
        // Arrange: Mock repository to return empty optional
        when(experienceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Expect RuntimeException when experience not found
        Exception exception = assertThrows(RuntimeException.class, () -> {
            experienceService.updateExperience(1L, createExperienceDto);
        });

        assertEquals("Could not get experience.", exception.getMessage());
        verify(experienceRepository).findById(anyLong());
    }

    @Test
    public void testDeleteExperience_Success() {
        // Arrange: Mock repository to return existing experience
        when(experienceRepository.findById(anyLong())).thenReturn(Optional.of(mockExperience));

        // Act: Call the delete method
        experienceService.deleteExperience(1L);

        // Assert: Verify experience was deleted
        verify(experienceRepository).delete(mockExperience);
        assertNull(mockExperience.getLawyer());
    }

    @Test
    public void testDeleteExperience_NotFound() {
        // Arrange: Mock repository to return empty optional
        when(experienceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Expect RuntimeException when experience not found
        Exception exception = assertThrows(RuntimeException.class, () -> {
            experienceService.deleteExperience(1L);
        });

        assertEquals("Could not get experience.", exception.getMessage());
        verify(experienceRepository).findById(anyLong());
    }
}
