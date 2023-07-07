
package com.auth.integration.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class SlackServiceTest {

    private SlackService slackService;

    @Mock
    private SlackService slackServiceMock;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
        slackService = new SlackService();
    }

    @Test
    public void testSendSlackMessage() {
        String email = "test@example.com";
        slackService.sendSlackMessage(email);
        verify(slackServiceMock).sendSlackMessage(email);
    }
}
