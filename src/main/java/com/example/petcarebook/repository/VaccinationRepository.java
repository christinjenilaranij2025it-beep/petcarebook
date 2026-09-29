package com.example.petcarebook.repository;

import com.example.petcarebook.model.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccinationRepository
        extends JpaRepository<Vaccination, Long> {

    List<Vaccination> findByNextDueDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    List<Vaccination> findByPetIdOrderByVaccinationDateDesc(
            Long petId
    );
}