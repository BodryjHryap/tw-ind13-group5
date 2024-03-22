package com.skypro.twind13group5.controller;

import com.skypro.twind13group5.enums.*;
import com.skypro.twind13group5.model.Adopter;
import com.skypro.twind13group5.model.Pet;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.service.AdopterService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AdopterControllerTest {

    private static final String CORRECT_USER_NAME = "Nick";
    private static final long CORRECT_USER_ID = 123456789;
    private static final UserType CORRECT_USER_TYPE = UserType.DEFAULT;
    private static final UserStatus CORRECT_USER_STATUS = UserStatus.APPROVE;
    private User user;
    private static final String CORRECT_NICK_NAME = "Nick";
    private static final Gender CORRECT_GENDER = Gender.MALE;
    private static final PetType CORRECT_PET_TYPE = PetType.DOG;
    private static final String CORRECT_COLOR = "BLACK";
    private static final String CORRECT_BREED = "Tax";
    private Pet pet;
    private Adopter adopter;
    private static final Long CORRECT_TEST_ID = 1L;

    @MockBean
    private AdopterService adopterService;
    private AdopterController adopterController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        adopterController = new AdopterController(adopterService);
    }

    @Test
    public void saveNewAdopterReturnsOk() throws Exception {

        AdopterController controller = new AdopterController(adopterService);

        pet = new Pet(CORRECT_PET_TYPE,
                CORRECT_NICK_NAME,
                CORRECT_COLOR,
                CORRECT_GENDER,
                CORRECT_BREED);

        user = new User(CORRECT_USER_ID,
                CORRECT_USER_NAME,
                CORRECT_USER_TYPE,
                CORRECT_USER_STATUS);

        adopter = new Adopter(user,
                pet);

        when(adopterService.saveAdopter(adopter))
                .thenReturn(adopter);

        ResponseEntity<Adopter> response = controller
                .saveNewAdopter(adopter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adopter, response.getBody());
    }

    @Test
    public void testGetAllAdopter() {

        Adopter adopter1 = new Adopter(user, pet);
        List<Adopter> adopters = new ArrayList<>();
        adopters.add(adopter);
        adopters.add(adopter1);
        when(adopterService.getAllAdopter()).thenReturn(adopters);

        ResponseEntity<Collection<Adopter>> response = adopterController.getAllAdopter();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Test
    public void checkingFoundAdopterByIdSuccess() {
        Adopter testAdopter = new Adopter();
        testAdopter.setId(CORRECT_TEST_ID);
        ResponseEntity<Adopter> expectedResponse = ResponseEntity.ok(testAdopter);
        Mockito.when(adopterService.foundAdopterById(CORRECT_TEST_ID)).thenReturn(testAdopter);

        ResponseEntity<Adopter> actualResponse = adopterController.foundAdopterById(CORRECT_TEST_ID);

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    public void checkingFoundAdopterByIdNotFound() {
        ResponseEntity<Adopter> expectedResponse = ResponseEntity.notFound().build();
        Mockito.doThrow(new RuntimeException()).when(adopterService).foundAdopterById(CORRECT_TEST_ID);

        ResponseEntity<Adopter> actualResponse = adopterController.foundAdopterById(CORRECT_TEST_ID);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void checkingGetAllAdoptersNotFound() {
        ResponseEntity<Collection<Adopter>> expectedResponse = ResponseEntity.notFound().build();
        Mockito.doThrow(new RuntimeException()).when(adopterService).getAllAdopter();

        ResponseEntity<Collection<Adopter>> actualResponse = adopterController.getAllAdopter();

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    public void checkingDeleteAdopterByIdSuccess() {
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        ResponseEntity<Void> actualResponse = adopterController.deleteAdopterById(CORRECT_TEST_ID);

        Mockito.verify(adopterService).deleteAdopterById(CORRECT_TEST_ID);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void checkingDeleteAdopterByIdNotFound() {
        ResponseEntity<Void> expectedResponse = ResponseEntity.notFound().build();
        Mockito.doThrow(new RuntimeException()).when(adopterService).deleteAdopterById(CORRECT_TEST_ID);

        ResponseEntity<Void> actualResponse = adopterController.deleteAdopterById(CORRECT_TEST_ID);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void checkingUpdateAdopterNotFound() {
        User user = new User();
        Pet pet = new Pet();
        ResponseEntity<Void> expectedResponse = ResponseEntity.notFound().build();
        Mockito.doThrow(new RuntimeException()).when(adopterService).updateAdopterById(CORRECT_TEST_ID, user, pet);

        ResponseEntity<Void> actualResponse = adopterController.updateAdopter(CORRECT_TEST_ID, user, pet);

        assertEquals(expectedResponse, actualResponse);
    }
}