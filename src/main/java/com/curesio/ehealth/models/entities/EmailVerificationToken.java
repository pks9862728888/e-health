package com.curesio.ehealth.models.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name = "email_verification_token")
@Table(name = "email_verification_token")
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_on", nullable = false)
    private OffsetDateTime createdOn;

    @Column(name = "expires_on", nullable = false)
    private OffsetDateTime expiresOn;

    @Column(name = "is_sent", nullable = false)
    private boolean isSent;

    @Column(name = "reason")
    private String reason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public EmailVerificationToken() {}

    public EmailVerificationToken(long id, String token, OffsetDateTime createdOn, OffsetDateTime expiresOn, boolean isSent, String reason, User user) {
        this.id = id;
        this.token = token;
        this.createdOn = createdOn;
        this.expiresOn = expiresOn;
        this.isSent = isSent;
        this.reason = reason;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public OffsetDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(OffsetDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
