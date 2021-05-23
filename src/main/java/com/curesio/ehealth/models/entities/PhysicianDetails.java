package com.curesio.ehealth.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "physician_details")
@Table(name = "physician_details")
public class PhysicianDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fees")
    private BigDecimal fees;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public PhysicianDetails() {}

    public PhysicianDetails(long id, BigDecimal fees, int yearsOfExperience, User user) {
        this.id = id;
        this.fees = fees;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
