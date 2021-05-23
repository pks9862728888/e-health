package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.AccountTypeEnum;
import com.curesio.ehealth.enumerations.AuthoritiesEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private String userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "account_type", nullable = false)
    private AccountTypeEnum accountType;

    @Column(name = "role")
    private AuthoritiesEnum role;

    @Column(name = "authorities", nullable = false)
    private ArrayList<String> authorities;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_non_locked")
    private boolean isNonLocked;

    @Column(name = "is_phone_verified", nullable = false)
    private boolean isPhoneVerified;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserDetails userDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KycStatus kycStatus;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserKycDocuments userKycDocuments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ResourceKycDocuments> resourceKycDocuments;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private HospitalDetails hospitalDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LaboratoryDetails laboratoryDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PhysicianDetails physicianDetails;

    public User() {}

    public User(long id, String userId, String username, String email, String password, String phone, AccountTypeEnum accountType, AuthoritiesEnum role, ArrayList<String> authorities, boolean isActive, boolean isNonLocked, boolean isPhoneVerified, UserDetails userDetails, KycStatus kycStatus, UserKycDocuments userKycDocuments, List<ResourceKycDocuments> resourceKycDocuments, HospitalDetails hospitalDetails, LaboratoryDetails laboratoryDetails, PhysicianDetails physicianDetails) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.accountType = accountType;
        this.role = role;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNonLocked = isNonLocked;
        this.isPhoneVerified = isPhoneVerified;
        this.userDetails = userDetails;
        this.kycStatus = kycStatus;
        this.userKycDocuments = userKycDocuments;
        this.resourceKycDocuments = resourceKycDocuments;
        this.hospitalDetails = hospitalDetails;
        this.laboratoryDetails = laboratoryDetails;
        this.physicianDetails = physicianDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public AuthoritiesEnum getRole() {
        return role;
    }

    public void setRole(AuthoritiesEnum role) {
        this.role = role;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNonLocked() {
        return isNonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        isNonLocked = nonLocked;
    }

    public boolean isPhoneVerified() {
        return isPhoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }

    public UserKycDocuments getUserKycDocuments() {
        return userKycDocuments;
    }

    public void setUserKycDocuments(UserKycDocuments userKycDocuments) {
        this.userKycDocuments = userKycDocuments;
    }

    public List<ResourceKycDocuments> getResourceKycDocuments() {
        return resourceKycDocuments;
    }

    public void setResourceKycDocuments(List<ResourceKycDocuments> resourceKycDocuments) {
        this.resourceKycDocuments = resourceKycDocuments;
    }

    public HospitalDetails getHospitalDetails() {
        return hospitalDetails;
    }

    public void setHospitalDetails(HospitalDetails hospitalDetails) {
        this.hospitalDetails = hospitalDetails;
    }

    public LaboratoryDetails getLaboratoryDetails() {
        return laboratoryDetails;
    }

    public void setLaboratoryDetails(LaboratoryDetails laboratoryDetails) {
        this.laboratoryDetails = laboratoryDetails;
    }

    public PhysicianDetails getPhysicianDetails() {
        return physicianDetails;
    }

    public void setPhysicianDetails(PhysicianDetails physicianDetails) {
        this.physicianDetails = physicianDetails;
    }
}
