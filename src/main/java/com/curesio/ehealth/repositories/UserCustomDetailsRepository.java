package com.curesio.ehealth.repositories;

import com.curesio.ehealth.models.entities.UserCustomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCustomDetailsRepository extends JpaRepository<UserCustomDetails, Long> {}
