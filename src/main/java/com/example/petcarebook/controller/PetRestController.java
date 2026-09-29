package com.example.petcarebook.controller;

import com.example.petcarebook.model.Pet;
import com.example.petcarebook.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetRestController {

    private final PetService petService;

    public PetRestController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPet(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petService.savePet(pet);
    }

    @PutMapping("/{id}")
    public Pet updatePet(
            @PathVariable Long id,
            @RequestBody Pet pet) {

        pet.setId(id);

        return petService.savePet(pet);
    }

    @DeleteMapping("/{id}")
    public String deletePet(@PathVariable Long id) {

        petService.deletePet(id);

        return "Pet deleted successfully";
    }
}