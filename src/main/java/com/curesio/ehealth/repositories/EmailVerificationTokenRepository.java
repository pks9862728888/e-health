package com.curesio.ehealth.repositories;

import com.curesio.ehealth.models.entities.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
}
