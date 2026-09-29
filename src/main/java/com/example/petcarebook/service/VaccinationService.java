package com.example.petcarebook.service;

import com.example.petcarebook.model.Vaccination;
import com.example.petcarebook.repository.VaccinationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccinationService {

    private final VaccinationRepository repository;

    public VaccinationService(VaccinationRepository repository) {
        this.repository = repository;
    }

    public List<Vaccination> getAllVaccinations() {
        return repository.findAll();
    }

    public Vaccination saveVaccination(Vaccination vaccination) {

        if (vaccination.getVaccinationDate() == null) {
            throw new RuntimeException(
                    "Vaccination date cannot be empty");
        }

        int intervalDays =
                getVaccineInterval(vaccination.getVaccineName());

        vaccination.setIntervalDays(intervalDays);

        LocalDate nextDate =
                vaccination.getVaccinationDate()
                        .plusDays(intervalDays);

        vaccination.setNextDueDate(nextDate);

        return repository.save(vaccination);
    }

    private int getVaccineInterval(String vaccineName) {

        if (vaccineName == null) {
            return 365;
        }

        String vaccine =
                vaccineName.trim().toLowerCase();

        if (vaccine.contains("rabies")) {
            return 365;
        }

        if (vaccine.contains("dhpp")) {
            return 365;
        }

        if (vaccine.contains("fvrcp")) {
            return 365;
        }

        if (vaccine.contains("booster")) {
            return 180;
        }

        return 365;
    }

    public Vaccination getVaccinationById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vaccination not found"));
    }

    public void deleteVaccination(Long id) {
        repository.deleteById(id);
    }

    public List<Vaccination> getDueWithinNext7Days() {

        LocalDate today = LocalDate.now();

        LocalDate sevenDaysLater =
                today.plusDays(7);

        return repository.findByNextDueDateBetween(
                today,
                sevenDaysLater
        );
    }

    public List<Vaccination> getVaccinationsForPet(Long petId) {
        return repository
                .findByPetIdOrderByVaccinationDateDesc(petId);
    }
}