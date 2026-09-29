package com.example.petcarebook.service;

import com.example.petcarebook.model.Pet;
import com.example.petcarebook.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    public Pet getPetById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pet not found"));
    }

    public Pet savePet(Pet pet) {
        return repository.save(pet);
    }

    public void deletePet(Long id) {
        repository.deleteById(id);
    }
}