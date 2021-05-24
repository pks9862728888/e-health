package com.curesio.ehealth.repositories;

import com.curesio.ehealth.enumerations.AccountTypeEnum;
import com.curesio.ehealth.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    int countAllByUserUniqueId(String userUniqueId);
    int countAllByEmail(String email);
    int countAllByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM USER u WHERE u.email = ?1 and u.account_type <> ?2", nativeQuery = true)
    int countAllByEmailAndNotAccountType(String email, AccountTypeEnum accountType);

}
