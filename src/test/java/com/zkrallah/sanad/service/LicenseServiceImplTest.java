package com.zkrallah.sanad.service;

import com.zkrallah.sanad.dtos.CreateLicenseDto;
import com.zkrallah.sanad.dtos.CreateLicenseDto;
import com.zkrallah.sanad.entity.License;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.License;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.LicenseRepository;
import com.zkrallah.sanad.service.license.LicenseServiceImpl;
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
public class LicenseServiceImplTest {

    @InjectMocks
    private LicenseServiceImpl licenseService;

    @Mock
    private UserService userService;

    @Mock
    private LicenseRepository licenseRepository;

    private CreateLicenseDto createLicenseDto;
    private User mockUser;
    private Lawyer mockLawyer;
    private License mockLicense;

    @BeforeEach
    public void setup() {
        // Set up CreateLicenseDto with dummy data
        createLicenseDto = new CreateLicenseDto();
        createLicenseDto.setNumber(1);
        createLicenseDto.setCountry("Mahalla El Dawla");
        createLicenseDto.setExpiresAt("2020-02-22");
        createLicenseDto.setIssuedAt("2020-02-22");

        // Mock Lawyer and User
        mockLawyer = new Lawyer();
        mockUser = new User();
        mockUser.setLawyer(mockLawyer);

        // Mock License object
        mockLicense = new License();
        mockLicense.setId(1L);
        mockLicense.setCountry("Dawlet El Mahalla");
        mockLicense.setLawyer(mockLawyer);
    }

    @Test
    public void testCreateLicense_Success() throws ParseException {
        // Arrange: Mock user service to return the mock user
        when(userService.getUserByJwt(anyString())).thenReturn(mockUser);
        when(licenseRepository.save(any(License.class))).thenReturn(mockLicense);

        // Act: Call the service method
        License createdLicense = licenseService.createLicense("Bearer some-jwt", createLicenseDto);

        // Assert: Verify the license was saved and properties are correct
        assertNotNull(createdLicense);
        assertEquals(mockLicense.getNumber(), createdLicense.getNumber());
        assertEquals(mockLicense.getCountry(), createdLicense.getCountry());

        // Verify interactions with mocks
        verify(userService).getUserByJwt(anyString());
        verify(licenseRepository).save(any(License.class));
    }

    @Test
    public void testCreateLicense_NotALawyer() {
        // Arrange: Set user without a lawyer and mock
        mockUser.setLawyer(null);
        when(userService.getUserByJwt(anyString())).thenReturn(mockUser);

        // Act & Assert: Verify the exception is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            licenseService.createLicense("Bearer some-jwt", createLicenseDto);
        });

        assertEquals("User is not a lawyer.", exception.getMessage());
        verify(userService).getUserByJwt(anyString());
        verifyNoInteractions(licenseRepository);
    }

    @Test
    public void testUpdateLicense_Success() throws ParseException {
        // Arrange: Mock repository to return existing license
        when(licenseRepository.findById(anyLong())).thenReturn(Optional.of(mockLicense));

        // Act: Call the service method
        License updatedLicense = licenseService.updateLicense(1L, createLicenseDto);

        // Assert: Verify properties are updated correctly
        assertNotNull(updatedLicense);
        assertEquals(mockLicense.getNumber(), updatedLicense.getNumber());
        assertEquals(mockLicense.getCountry(), updatedLicense.getCountry());

        // Verify interactions
        verify(licenseRepository).findById(anyLong());
    }

    @Test
    public void testUpdateLicense_NotFound() throws ParseException {
        // Arrange: Mock repository to return empty optional
        when(licenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Expect RuntimeException when license not found
        Exception exception = assertThrows(RuntimeException.class, () -> {
            licenseService.updateLicense(1L, createLicenseDto);
        });

        assertEquals("Failed to get license.", exception.getMessage());
        verify(licenseRepository).findById(anyLong());
    }

    @Test
    public void testDeleteLicense_Success() {
        // Arrange: Mock repository to return existing license
        when(licenseRepository.findById(anyLong())).thenReturn(Optional.of(mockLicense));

        // Act: Call the delete method
        licenseService.deleteLicense(1L);

        // Assert: Verify license was deleted
        verify(licenseRepository).delete(mockLicense);
        assertNotNull(mockLicense.getLawyer());
    }

    @Test
    public void testDeleteLicense_NotFound() {
        // Arrange: Mock repository to return empty optional
        when(licenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Expect RuntimeException when license not found
        Exception exception = assertThrows(RuntimeException.class, () -> {
            licenseService.deleteLicense(1L);
        });

        assertEquals("Failed to get license", exception.getMessage());
        verify(licenseRepository).findById(anyLong());
    }
}
