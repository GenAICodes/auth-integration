
package com.auth.integration.service;

import com.auth.integration.exception.DuplicateEmailException;
import com.auth.integration.model.User;
import com.auth.integration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    private final UserRepository userRepository;

    @Autowired
    public UserValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new DuplicateEmailException("Email already exists");
        }
    }
}
