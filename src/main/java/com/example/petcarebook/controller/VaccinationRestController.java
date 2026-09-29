package com.example.petcarebook.controller;

import com.example.petcarebook.model.Pet;
import com.example.petcarebook.model.Vaccination;
import com.example.petcarebook.service.PetService;
import com.example.petcarebook.service.VaccinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationRestController {

    private final VaccinationService vaccinationService;
    private final PetService petService;

    public VaccinationRestController(
            VaccinationService vaccinationService,
            PetService petService) {

        this.vaccinationService = vaccinationService;
        this.petService = petService;
    }

    @GetMapping
    public List<Vaccination> getAllVaccinations() {
        return vaccinationService.getAllVaccinations();
    }

    @GetMapping("/{id}")
    public Vaccination getVaccination(
            @PathVariable Long id) {

        return vaccinationService
                .getVaccinationById(id);
    }

    @PostMapping
    public Vaccination createVaccination(
            @RequestParam Long petId,
            @RequestBody Vaccination vaccination) {

        Pet pet = petService.getPetById(petId);

        vaccination.setPet(pet);

        return vaccinationService
                .saveVaccination(vaccination);
    }

    @DeleteMapping("/{id}")
    public String deleteVaccination(
            @PathVariable Long id) {

        vaccinationService.deleteVaccination(id);

        return "Vaccination deleted successfully";
    }

    @GetMapping("/pet/{petId}")
    public List<Vaccination> getPetHistory(
            @PathVariable Long petId) {

        return vaccinationService
                .getVaccinationsForPet(petId);
    }

    @GetMapping("/upcoming")
    public List<Vaccination> getUpcomingVaccinations() {

        return vaccinationService
                .getDueWithinNext7Days();
    }
}