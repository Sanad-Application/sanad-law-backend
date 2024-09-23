package com.zkrallah.sanad.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import com.zkrallah.sanad.service.education.EducationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zkrallah.sanad.dtos.CreateEducationDto;
import com.zkrallah.sanad.entity.Education;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.EducationRepository;
import com.zkrallah.sanad.service.user.UserService;

@ExtendWith(MockitoExtension.class)
public class EducationServiceImplTest {

    @InjectMocks
    private EducationServiceImpl educationService;

    @Mock
    private UserService userService;

    @Mock
    private EducationRepository educationRepository;

    private CreateEducationDto createEducationDto;
    private User mockUser;
    private Lawyer mockLawyer;
    private Education mockEducation;

    @BeforeEach
    public void setup() {
        // Set up CreateEducationDto with dummy data
        createEducationDto = new CreateEducationDto();
        createEducationDto.setTitle("Bachelor of Law");
        createEducationDto.setUniversity("Harvard University");
        createEducationDto.setGraduation("15-06-2020");

        // Mock Lawyer and User
        mockLawyer = new Lawyer();
        mockUser = new User();
        mockUser.setLawyer(mockLawyer);

        // Mock Education object
        mockEducation = new Education();
        mockEducation.setId(1L);
        mockEducation.setTitle("Old Title");
        mockEducation.setUniversity("Old University");
        mockEducation.setLawyer(mockLawyer);
    }

    @Test
    public void testCreateEducation_Success() throws ParseException {
        // Arrange: Mock user service to return the mock user
        when(userService.getUserByJwt(anyString())).thenReturn(mockUser);
        when(educationRepository.save(any(Education.class))).thenReturn(mockEducation);

        // Act: Call the service method
        Education createdEducation = educationService.createEducation("Bearer some-jwt", createEducationDto);

        // Assert: Verify the education was saved and properties are correct
        assertNotNull(createdEducation);
        assertEquals(mockEducation.getTitle(), createdEducation.getTitle());
        assertEquals(mockEducation.getUniversity(), createdEducation.getUniversity());

        // Verify interactions with mocks
        verify(userService).getUserByJwt(anyString());
        verify(educationRepository).save(any(Education.class));
    }

    @Test
    public void testCreateEducation_NotALawyer() {
        // Arrange: Set user without a lawyer and mock
        mockUser.setLawyer(null);
        when(userService.getUserByJwt(anyString())).thenReturn(mockUser);

        // Act & Assert: Verify the exception is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            educationService.createEducation("Bearer some-jwt", createEducationDto);
        });

        assertEquals("User is not a lawyer.", exception.getMessage());
        verify(userService).getUserByJwt(anyString());
        verifyNoInteractions(educationRepository);
    }

    @Test
    public void testUpdateEducation_Success() throws ParseException {
        // Arrange: Mock repository to return existing education
        when(educationRepository.findById(anyLong())).thenReturn(Optional.of(mockEducation));

        // Act: Call the service method
        Education updatedEducation = educationService.updateEducation(1L, createEducationDto);

        // Assert: Verify properties are updated correctly
        assertNotNull(updatedEducation);
        assertEquals(createEducationDto.getTitle(), updatedEducation.getTitle());
        assertEquals(createEducationDto.getUniversity(), updatedEducation.getUniversity());

        // Verify interactions
        verify(educationRepository).findById(anyLong());
    }

    @Test
    public void testUpdateEducation_NotFound() throws ParseException {
        // Arrange: Mock repository to return empty optional
        when(educationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Expect RuntimeException when education not found
        Exception exception = assertThrows(RuntimeException.class, () -> {
            educationService.updateEducation(1L, createEducationDto);
        });

        assertEquals("Could not get education.", exception.getMessage());
        verify(educationRepository).findById(anyLong());
    }

    @Test
    public void testDeleteEducation_Success() {
        // Arrange: Mock repository to return existing education
        when(educationRepository.findById(anyLong())).thenReturn(Optional.of(mockEducation));

        // Act: Call the delete method
        educationService.deleteEducation(1L);

        // Assert: Verify education was deleted
        verify(educationRepository).delete(mockEducation);
        assertNull(mockEducation.getLawyer());
    }

    @Test
    public void testDeleteEducation_NotFound() {
        // Arrange: Mock repository to return empty optional
        when(educationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Expect RuntimeException when education not found
        Exception exception = assertThrows(RuntimeException.class, () -> {
            educationService.deleteEducation(1L);
        });

        assertEquals("Could not get education.", exception.getMessage());
        verify(educationRepository).findById(anyLong());
    }
}
