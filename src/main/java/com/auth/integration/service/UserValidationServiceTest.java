
package com.auth.integration.service;

import com.auth.integration.exception.DuplicateEmailException;
import com.auth.integration.model.User;
import com.auth.integration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserValidationServiceTest {

    private UserValidationService userValidationService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userValidationService = new UserValidationService(userRepository);
    }

    @Test
    public void testValidateUser_ThrowsDuplicateEmailException() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertThrows(DuplicateEmailException.class, () -> {
            userValidationService.validateUser(user);
        });
    }

    @Test
    public void testValidateUser_NoDuplicateEmailException() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        userValidationService.validateUser(user);
    }
}
