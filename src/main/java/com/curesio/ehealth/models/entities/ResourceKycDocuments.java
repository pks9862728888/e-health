package com.curesio.ehealth.models.entities;

import com.curesio.ehealth.enumerations.ResourceKycDocumentFileTypeEnum;

import javax.persistence.*;

@Entity(name = "resource_kyc_documents")
public class ResourceKycDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "document_file_type", nullable = false)
    private ResourceKycDocumentFileTypeEnum documentFileType;

    @Column(name = "file", nullable = false)
    private String file;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ResourceKycDocuments() {}

    public ResourceKycDocuments(long id, ResourceKycDocumentFileTypeEnum documentFileType, String file, User user) {
        this.id = id;
        this.documentFileType = documentFileType;
        this.file = file;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ResourceKycDocumentFileTypeEnum getDocumentFileType() {
        return documentFileType;
    }

    public void setDocumentFileType(ResourceKycDocumentFileTypeEnum documentFileType) {
        this.documentFileType = documentFileType;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
