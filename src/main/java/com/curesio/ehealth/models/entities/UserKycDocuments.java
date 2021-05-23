package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.KycDocumentFileTypeEnum;
import com.curesio.ehealth.enumerations.UserKycDocumentTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity(name = "kyc_user_documents")
@Table(name = "kyc_user_documents")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserKycDocuments {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "document_type")
    @JsonProperty("document_type")
    private UserKycDocumentTypeEnum documentType;

    @Column(name ="id_front")
    @JsonProperty("id_front")
    private String idFront;

    @Column(name = "id_back")
    @JsonProperty("id_back")
    private String idBack;

    @Column(name = "id_front_file_type")
    @JsonProperty("id_front_file_type")
    private KycDocumentFileTypeEnum idFrontFileType;

    @Column(name = "id_back_file_type")
    @JsonProperty("id_back_file_type")
    private KycDocumentFileTypeEnum idBackFileType;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserKycDocuments() {}

    public UserKycDocuments(long id, UserKycDocumentTypeEnum documentType, String idFront, String idBack, KycDocumentFileTypeEnum idFrontFileType, KycDocumentFileTypeEnum idBackFileType, User user) {
        this.id = id;
        this.documentType = documentType;
        this.idFront = idFront;
        this.idBack = idBack;
        this.idFrontFileType = idFrontFileType;
        this.idBackFileType = idBackFileType;
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

    public KycDocumentFileTypeEnum getIdFrontFileType() {
        return idFrontFileType;
    }

    public void setIdFrontFileType(KycDocumentFileTypeEnum idFrontFileType) {
        this.idFrontFileType = idFrontFileType;
    }

    public KycDocumentFileTypeEnum getIdBackFileType() {
        return idBackFileType;
    }

    public void setIdBackFileType(KycDocumentFileTypeEnum idBackFileType) {
        this.idBackFileType = idBackFileType;
    }
}
