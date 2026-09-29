package com.example.petcarebook.controller;

import com.example.petcarebook.model.Pet;
import com.example.petcarebook.service.PetService;
import com.example.petcarebook.service.VaccinationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetController {

    private final PetService petService;
    private final VaccinationService vaccinationService;

    public PetController(
            PetService petService,
            VaccinationService vaccinationService) {

        this.petService = petService;
        this.vaccinationService = vaccinationService;
    }

    @GetMapping("/pets/add")
    public String addPetPage(
            Model model,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        model.addAttribute("pet", new Pet());

        return "add-pet";
    }

    @PostMapping("/pets/save")
    public String savePet(
            @ModelAttribute Pet pet,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        petService.savePet(pet);

        return "redirect:/";
    }

    @GetMapping("/pets/edit/{id}")
    public String editPet(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "pet",
                petService.getPetById(id)
        );

        return "add-pet";
    }

    @PostMapping("/pets/update/{id}")
    public String updatePet(
            @PathVariable Long id,
            @ModelAttribute Pet pet,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        pet.setId(id);

        petService.savePet(pet);

        return "redirect:/";
    }

    @GetMapping("/pets/delete/{id}")
    public String deletePet(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        petService.deletePet(id);

        return "redirect:/";
    }

    @GetMapping("/pets/history/{id}")
    public String petHistory(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        Pet pet = petService.getPetById(id);

        model.addAttribute("pet", pet);

        model.addAttribute(
                "vaccinations",
                vaccinationService.getVaccinationsForPet(id)
        );

        return "pet-history";
    }
}