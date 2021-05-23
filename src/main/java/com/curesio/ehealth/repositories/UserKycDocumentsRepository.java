package com.curesio.ehealth.repositories;

import com.curesio.ehealth.models.entities.UserKycDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserKycDocumentsRepository extends JpaRepository<UserKycDocuments, Long> {
}
