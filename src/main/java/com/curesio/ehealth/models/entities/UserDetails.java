package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.CountryEnum;
import com.curesio.ehealth.enumerations.GenderEnum;
import com.curesio.ehealth.enumerations.StateEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user_details")
@Table(name = "user_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserDetails {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private GenderEnum gender;

    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private StateEnum state;

    @Column(name = "country", nullable = false)
    private CountryEnum country;

    @Column(name = "pin_code")
    private String pinCode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserDetails() {}

    public UserDetails(long id, String firstName, String lastName, GenderEnum gender, Date dateOfBirth, String address, StateEnum state, CountryEnum country, String pinCode, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
