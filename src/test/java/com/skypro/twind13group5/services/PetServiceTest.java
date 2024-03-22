package com.skypro.twind13group5.services;

import com.skypro.twind13group5.enums.Gender;
import com.skypro.twind13group5.enums.PetType;
import com.skypro.twind13group5.model.Pet;
import com.skypro.twind13group5.repository.PetRepository;
import com.skypro.twind13group5.service.PetService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    private PetService petService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.petService = new PetService(petRepository);
    }

    @Test
    public void testGetAllPets() {
        Pet pet1 = new Pet(PetType.DOG, String.format("Bobik"), String.format("WHITE"), Gender.MALE, String.format("Tax"));
        Pet pet2 = new Pet(PetType.CAT, String.format("Barsik"), String.format("BLACK"), Gender.FEMALE, String.format("Scottish"));

        when(petRepository.findAll()).thenReturn(Lists.newArrayList(pet1, pet2));

        Collection<Pet> result = petService.getAllPets();

        verify(petRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(true, result.contains(pet1));
        assertEquals(true, result.contains(pet2));
    }
}
