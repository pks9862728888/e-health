package com.curesio.ehealth.listeners;

import com.curesio.ehealth.events.SendEmailVerificationMailEvent;
import com.curesio.ehealth.models.entities.EmailVerificationToken;
import com.curesio.ehealth.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
public class SendEmailVerificationMailEventListener implements ApplicationListener<SendEmailVerificationMailEvent> {

    @Value("${email-verification-token-expiry-days}")
    private String emailVerificationTokenExpiryDays;

    @Value("spring.mail.username")
    private String fromEmail;

    private UserService userService;
    private JavaMailSender javaMailSender;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public SendEmailVerificationMailEventListener(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void onApplicationEvent(SendEmailVerificationMailEvent event) {
        Optional<EmailVerificationToken> optionalEmailVerificationToken = userService.generateVerificationTokenAndSaveToDb(event.getUserId());

        if (optionalEmailVerificationToken.isEmpty()) {
            return;
        }

        // Generate message
        String recipientEmail = event.getEmail();
        String subject = "e-Health Email Verification!";
        String tokenUrl = userService.generateUrlFromVerificationToken(optionalEmailVerificationToken.get().getToken(), event.getUserUniqueId());
        String message = String.format(
                "Hi,\n\nPlease click on this link to verify your email:\n%s\n\nIf you think that this is a mistake then please ignore.\n\nThis link is valid for the next %s days.\nIf link is expired then click on forgot password to verify your account.\n\nThanking you,\nRegards,\nE-Health Team",
                tokenUrl,
                emailVerificationTokenExpiryDays
        );

        // Generate mail
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setSentDate(new Date());
        mailMessage.setReplyTo(fromEmail);
        mailMessage.setText(message);
        LOGGER.info("Sending verification mail!");

        // Send mail and update status in database
        try {
            javaMailSender.send(mailMessage);
            userService.updateMailSentStatus(optionalEmailVerificationToken.get().getId(), true, "");
        } catch (Exception e) {
            LOGGER.error("Error occurred while sending verification mail.");
            LOGGER.error(e.getCause().getMessage());
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            userService.updateMailSentStatus(optionalEmailVerificationToken.get().getId(), false, e.getCause().getMessage());
        }
    }

}
