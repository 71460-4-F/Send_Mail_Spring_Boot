package com.ms.email.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.email.models.Email;

public interface EmailRepository extends JpaRepository<Email, UUID> {

    @Query(value = "SELECT * FROM email e WHERE e.status_email = :statusEmail ;", nativeQuery = true)
    List<Email> findAllByStauts(@Param("statusEmail") Integer statusEmail);
}
