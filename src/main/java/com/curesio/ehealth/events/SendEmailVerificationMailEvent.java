package com.curesio.ehealth.events;

import org.springframework.context.ApplicationEvent;

public class SendEmailVerificationMailEvent extends ApplicationEvent {

    private long userId;
    private String userUniqueId;
    private String email;

    public SendEmailVerificationMailEvent(Object source, long userId, String userUniqueId, String email) {
        super(source);
        this.userId = userId;
        this.userUniqueId = userUniqueId;
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }
}
