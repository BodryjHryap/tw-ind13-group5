package com.skypro.twind13group5.service;

import com.skypro.twind13group5.enums.Gender;
import com.skypro.twind13group5.enums.PetType;
import com.skypro.twind13group5.exception.NotFoundPetException;
import com.skypro.twind13group5.model.Pet;
import com.skypro.twind13group5.repository.PetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Сервис и бизнес-логика по работе с животными.
 */

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet savePet(PetType petType, String petName, String color, Gender gender, String breed) {

        Pet pet = new Pet(petType, petName, color, gender, breed);

        petRepository.save(pet);
        return pet;
    }

    @Transactional
    public void updatePetById(Long id,
                                 PetType petType,
                                 String petName,
                                 String color,
                                 Gender gender,
                                 String breed) {
        Pet pet = petRepository.getReferenceById(id);
        if (pet == null) {
            throw new NotFoundPetException("Животное не найдено!");
        }
        petRepository.updatePetById(id, petType, petName, color, gender, breed);
    }

    public void deletePetById(Long id) {

        Pet pet = petRepository.getReferenceById(id);
        if (pet == null) {
            throw new NotFoundPetException("Животное не найдено!");
        }
        petRepository.deleteById(id);
    }

    public Collection<Pet> getAllPets() {
        return petRepository.findAll();
    }
    public Collection<Pet> getAllDogs() {
        return petRepository.findAllDogs();
    }
    public Collection<Pet> getAllCats() {
        return petRepository.findAllCats();
    }

}
