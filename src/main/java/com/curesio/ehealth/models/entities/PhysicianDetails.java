package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "physician_details")
@Table(name = "physician_details")
public class PhysicianDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fees", nullable = false)
    private BigDecimal fees;

    @Column(name = "currency", nullable = false)
    private CurrencyEnum currency;

    @Column(name = "years_of_experience", nullable = false)
    @JsonProperty("years_of_experience")
    private int yearsOfExperience;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public PhysicianDetails() {}

    public PhysicianDetails(long id, BigDecimal fees, CurrencyEnum currency, int yearsOfExperience, User user) {
        this.id = id;
        this.fees = fees;
        this.currency = currency;
        this.yearsOfExperience = yearsOfExperience;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
