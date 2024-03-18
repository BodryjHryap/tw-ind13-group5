package com.skypro.twind13group5.controller;

import com.skypro.twind13group5.enums.Gender;
import com.skypro.twind13group5.enums.PetType;
import com.skypro.twind13group5.model.Pet;
import com.skypro.twind13group5.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PetControllerTest {

    public static final String NICK_NAME = "Шарик";
    public static final String NICK_NAME_TWO = "Мурзик";
    public static final Gender GENDER_CORRECT_ONE = Gender.MALE;
    public static final Gender GENDER_CORRECT_TWO = Gender.FEMALE;
    public static final PetType PET_CORRECT_ONE = PetType.DOG;
    public static final PetType PET_CORRECT_TWO = PetType.CAT;
    public static final String COLOR_CORRECT_ONE = "BLACK";
    public static final String COLOR_CORRECT_TWO = "WHITE";
    public static final String BREED_CORRECT_ONE = "Tax";
    public static final String BREED_CORRECT_TWO = "Dalmatian";

    private static final Long TEST_ID = 1L;

    @Test
    public void savePet_returns200_whenPetSavedSuccessfully() {
        PetService mockedService = mock(PetService.class);
        PetController controller = new PetController(mockedService);

        Pet pet = new Pet(PET_CORRECT_ONE ,NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE);
        when(mockedService.savePet(PET_CORRECT_ONE, NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE))
                .thenReturn(pet);

        ResponseEntity<Pet> response = controller
                .savePet(PET_CORRECT_ONE, NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pet, response.getBody());
    }
    @Test
    public void savePet_returnsOK_whenParamsAreMissing() {
        PetService mockedService = mock(PetService.class);
        PetController controller = new PetController(mockedService);

        ResponseEntity<Pet> response = controller.savePet(null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test public void savePet_returnsOK_whenParamsHaveInvalidFormat() {
        PetService mockedService = mock(PetService.class);
        PetController controller = new PetController(mockedService);

        ResponseEntity<Pet> response = controller
                .savePet(null, NICK_NAME, COLOR_CORRECT_ONE, null, BREED_CORRECT_ONE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test public void savePet_returns404_whenServiceThrowsException() {
        PetService mockedService = mock(PetService.class);
        PetController controller = new PetController(mockedService);

        when(mockedService.savePet(PET_CORRECT_ONE, NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE))
                .thenThrow(new RuntimeException());

        ResponseEntity<Pet> response = controller
                .savePet(PET_CORRECT_ONE, NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePetById() {

        PetService petServiceMock = mock(PetService.class);
        PetController petController = new PetController(petServiceMock);
        Long id = 1L;

        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        ResponseEntity<Void> actualResponse = petController
                .updateAdopter(id, PET_CORRECT_ONE, NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE);

        assertEquals(expectedResponse, actualResponse);
        verify(petServiceMock).updatePetById(id, PET_CORRECT_ONE, NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE);
    }

    @Mock
    private PetService petService;

    private PetController petController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        petController = new PetController(petService);
    }

    @Test
    public void testGetAllPet() {
        Pet pet1 = new Pet(1, PET_CORRECT_ONE, NICK_NAME, COLOR_CORRECT_ONE, GENDER_CORRECT_ONE, BREED_CORRECT_ONE);
        Pet pet2 = new Pet(2, PET_CORRECT_TWO, NICK_NAME_TWO, COLOR_CORRECT_TWO, GENDER_CORRECT_TWO, BREED_CORRECT_TWO);
        List<Pet> pets = new ArrayList<>();
        pets.add(pet1);
        pets.add(pet2);
        when(petService.getAllPets()).thenReturn(pets);

        ResponseEntity<Collection<Pet>> response = petController.getAllPets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void checkingDeleteAdopterByIdSuccess() {
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();
        Mockito.doNothing().when(petService).deletePetById(TEST_ID);

        ResponseEntity<Void> actualResponse = petController.deleteAdopterById(TEST_ID);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void checkingDeleteAdopterByIdNotFound() {
        ResponseEntity<Void> expectedResponse = ResponseEntity.notFound().build();
        Mockito.doThrow(new RuntimeException()).when(petService).deletePetById(TEST_ID);

        ResponseEntity<Void> actualResponse = petController.deleteAdopterById(TEST_ID);

        assertEquals(expectedResponse, actualResponse);
    }
}
