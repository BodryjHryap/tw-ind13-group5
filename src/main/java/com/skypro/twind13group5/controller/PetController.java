package com.skypro.twind13group5.controller;

import com.skypro.twind13group5.enums.Gender;
import com.skypro.twind13group5.enums.PetType;
import com.skypro.twind13group5.model.Pet;
import com.skypro.twind13group5.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/pet-shelter/pet")
@Tag(name = "API по работе с животными",
        description = "CRUD-операции для работы с животными")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Operation(
            summary = "Регистрация животного",
            description = "Нужно написать данные животного " +
                    "(кличка, тип животного, цвет и пол)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Удалось добавить животное"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Параметры запроса отсутствуют или имеют некорректный формат"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Произошла ошибка, не зависящая от вызывающей стороны"
    )
    public ResponseEntity<Pet> savePet(@RequestParam(required = false) PetType petType,
                                       @RequestParam(required = false) String petName,
                                       @RequestParam(required = false) String color,
                                       @RequestParam(required = false) Gender gender,
                                       @RequestParam(required = false) String breed) {
        try {
            return ResponseEntity.ok(petService.savePet(petType, petName, color, gender, breed));
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменения данных животного по идентификатору",
            description = "Нужно написать новые данные животного " +
                    "(кличка, тип животного, цвет и пол)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Удалось изменить данне животного"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Параметры запроса отсутствуют или имеют некорректный формат"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Произошла ошибка, не зависящая от вызывающей стороны"
    )
    public ResponseEntity<Void> updateAdopter(@PathVariable Long id,
                                              @RequestParam(required = false) PetType petType,
                                              @RequestParam(required = false) String petName,
                                              @RequestParam(required = false) String color,
                                              @RequestParam(required = false) Gender gender,
                                              @RequestParam(required = false) String breed) {

        try {
            petService.updatePetById(id, petType, petName, color, gender, breed);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление животного по идентификатору",
            description = "Нужно написать id животного, которого нужно удалить"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Удалось удалить животное"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Параметры запроса отсутствуют или имеют некорректный формат"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Произошла ошибка, не зависящая от вызывающей стороны"
    )
    public ResponseEntity<Void> deleteAdopterById(@PathVariable Long id) {

        try {
            petService.deletePetById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(
            summary = "Список всех животных"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Удалось получить список животных"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Параметры запроса отсутствуют или имеют некорректный формат"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Произошла ошибка, не зависящая от вызывающей стороны"
    )
    public ResponseEntity<Collection<Pet>> getAllPets() {

        try {
            return ResponseEntity.ok(petService.getAllPets());
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}