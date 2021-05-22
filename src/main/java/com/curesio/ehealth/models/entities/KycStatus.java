package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.ResourceKycStatusEnum;
import com.curesio.ehealth.enumerations.UserKycStatusEnum;

import javax.persistence.*;

@Entity(name = "kyc_status")
@Table(name = "kyc_status")
public class KycStatus {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_kyc_status", nullable = false)
    private UserKycStatusEnum userKycStatus;

    @Column(name = "resource_kyc_status", nullable = false)
    private ResourceKycStatusEnum resourceKycStatus;

    @Column(name = "reason_user_kyc_status")
    private String reasonUserKycStatus;

    @Column(name = "reason_resource_kyc_status")
    private String reasonResourceKycStatus;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public KycStatus() {}

    public KycStatus(long id, UserKycStatusEnum userKycStatus, ResourceKycStatusEnum resourceKycStatus, String reasonUserKycStatus, String reasonResourceKycStatus, User user) {
        this.id = id;
        this.userKycStatus = userKycStatus;
        this.resourceKycStatus = resourceKycStatus;
        this.reasonUserKycStatus = reasonUserKycStatus;
        this.reasonResourceKycStatus = reasonResourceKycStatus;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserKycStatusEnum getUserKycStatus() {
        return userKycStatus;
    }

    public void setUserKycStatus(UserKycStatusEnum userKycStatus) {
        this.userKycStatus = userKycStatus;
    }

    public ResourceKycStatusEnum getResourceKycStatus() {
        return resourceKycStatus;
    }

    public void setResourceKycStatus(ResourceKycStatusEnum resourceKycStatus) {
        this.resourceKycStatus = resourceKycStatus;
    }

    public String getReasonUserKycStatus() {
        return reasonUserKycStatus;
    }

    public void setReasonUserKycStatus(String reasonUserKycStatus) {
        this.reasonUserKycStatus = reasonUserKycStatus;
    }

    public String getReasonResourceKycStatus() {
        return reasonResourceKycStatus;
    }

    public void setReasonResourceKycStatus(String reasonResourceKycStatus) {
        this.reasonResourceKycStatus = reasonResourceKycStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
