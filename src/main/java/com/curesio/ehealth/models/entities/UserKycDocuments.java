package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.UserKycDocumentTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "kyc_user_documents")
@Table(name = "kyc_user_documents")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserKycDocuments {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "document_type")
    private UserKycDocumentTypeEnum documentType;

    @Column(name ="id_front")
    private String idFront;

    @Column(name = "id_back")
    private String idBack;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserKycDocuments() {}

    public UserKycDocuments(long id, UserKycDocumentTypeEnum documentType, String idFront, String idBack, User user) {
        this.id = id;
        this.documentType = documentType;
        this.idFront = idFront;
        this.idBack = idBack;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserKycDocumentTypeEnum getDocumentType() {
        return documentType;
    }

    public void setDocumentType(UserKycDocumentTypeEnum documentType) {
        this.documentType = documentType;
    }

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }

    public String getIdBack() {
        return idBack;
    }

    public void setIdBack(String idBack) {
        this.idBack = idBack;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
