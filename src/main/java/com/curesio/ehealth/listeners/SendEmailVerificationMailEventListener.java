package com.curesio.ehealth.listeners;

import com.curesio.ehealth.events.SendEmailVerificationMailEvent;
import com.curesio.ehealth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SendEmailVerificationMailEventListener implements ApplicationListener<SendEmailVerificationMailEvent> {

    @Value("${frontend-url}")
    private String frontEndUrl;

    private UserService userService;

    @Autowired
    public SendEmailVerificationMailEventListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(SendEmailVerificationMailEvent event) {
        Optional<String> optionalToken = userService.generateVerificationTokenAndSaveToDb(event.getUserInternalId());

        if (optionalToken.isEmpty()) {
            return;
        }

        String token = optionalToken.get();
    }

}
