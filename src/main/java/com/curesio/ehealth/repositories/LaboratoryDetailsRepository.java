package com.curesio.ehealth.repositories;

import com.curesio.ehealth.models.entities.LaboratoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoryDetailsRepository extends JpaRepository<LaboratoryDetails, Long> {
}
