package com.curesio.ehealth.repositories;

import com.curesio.ehealth.models.entities.UserKycStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserKycStatusRepository extends JpaRepository<UserKycStatus, Long> {
}
