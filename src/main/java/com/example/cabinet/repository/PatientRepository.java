package com.example.cabinet.repository;

import com.example.cabinet.entities.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> searchByScore(int score);
    @Modifying
    @Transactional
    @Query("UPDATE Patient p SET p.birthday = :bd WHERE p.id = :id")
    void updateByBirthday(@Param("id") long id, @Param("bd") Date bd);

    @Transactional
    void deleteById(long id);
}
