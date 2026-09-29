package com.example.petcarebook.controller;

import com.example.petcarebook.model.Pet;
import com.example.petcarebook.model.Vaccination;
import com.example.petcarebook.service.PetService;
import com.example.petcarebook.service.VaccinationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VaccinationController {

    private final VaccinationService vaccinationService;
    private final PetService petService;

    public VaccinationController(
            VaccinationService vaccinationService,
            PetService petService) {

        this.vaccinationService = vaccinationService;
        this.petService = petService;
    }

    @GetMapping("/")
    public String dashboard(
            Model model,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "pets",
                petService.getAllPets()
        );

        model.addAttribute(
                "upcomingVaccinations",
                vaccinationService.getDueWithinNext7Days()
        );

        return "index";
    }

    @GetMapping("/vaccinations/add")
    public String addVaccinationPage(
            Model model,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "vaccination",
                new Vaccination()
        );

        model.addAttribute(
                "pets",
                petService.getAllPets()
        );

        return "add-vaccination";
    }

    @PostMapping("/vaccinations/save")
    public String saveVaccination(
            @ModelAttribute Vaccination vaccination,
            @RequestParam Long petId,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        Pet pet = petService.getPetById(petId);

        vaccination.setPet(pet);

        vaccinationService.saveVaccination(vaccination);

        return "redirect:/";
    }

    @GetMapping("/vaccinations/delete/{id}")
    public String deleteVaccination(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        vaccinationService.deleteVaccination(id);

        return "redirect:/";
    }
}