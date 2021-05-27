package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.CountryEnum;
import com.curesio.ehealth.enumerations.StateEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity(name = "laboratory_details")
@Table(name = "laboratory_details")
public class LaboratoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "landmark", nullable = false)
    private String landmark;

    @Column(name = "state", nullable = false)
    private StateEnum state;

    @Column(name = "pin_code", nullable = false)
    @JsonProperty("pin_code")
    private String pinCode;

    @Column(name = "country", nullable = false)
    private CountryEnum country;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public LaboratoryDetails() {}

    public LaboratoryDetails(long id, String name, String landmark, StateEnum state, String pinCode, CountryEnum country, User user) {
        this.id = id;
        this.name = name;
        this.landmark = landmark;
        this.state = state;
        this.pinCode = pinCode;
        this.country = country;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}