package com.skypro.twind13group5.service;

import com.skypro.twind13group5.model.Pet;
import com.skypro.twind13group5.repository.PetRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Collection<Pet> getAllPets() {
        return petRepository.findAll();
    }

}
