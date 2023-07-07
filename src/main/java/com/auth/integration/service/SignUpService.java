
package com.auth.integration.service;

import com.auth.integration.exception.DuplicateEmailException;
import com.auth.integration.model.User;
import com.auth.integration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final SlackService slackService;

    @Autowired
    public SignUpService(UserRepository userRepository, UserValidationService userValidationService, SlackService slackService) {
        this.userRepository = userRepository;
        this.userValidationService = userValidationService;
        this.slackService = slackService;
    }

    public User signUp(User user) {
        userValidationService.validateUser(user);

        User createdUser = userRepository.save(user);

        slackService.sendSlackMessage(user.getEmail());

        return createdUser;
    }
}
