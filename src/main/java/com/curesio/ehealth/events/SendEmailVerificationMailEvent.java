package com.curesio.ehealth.events;

import org.springframework.context.ApplicationEvent;

public class SendEmailVerificationMailEvent extends ApplicationEvent {

    private long userId;

    public SendEmailVerificationMailEvent(Object source, long userId) {
        super(source);
        this.userId = userId;
    }

    public long getUserInternalId() {
        return userId;
    }
}
