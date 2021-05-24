package com.curesio.ehealth.repositories;

import com.curesio.ehealth.models.entities.PhysicianDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicianDetailsRepository extends JpaRepository<PhysicianDetails, Long> {
}