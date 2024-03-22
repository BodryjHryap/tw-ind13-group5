package com.skypro.twind13group5.repository;

import com.skypro.twind13group5.enums.Gender;
import com.skypro.twind13group5.enums.PetType;
import com.skypro.twind13group5.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Collection;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    /**
     * Изменяем животное по идентификатору
     *
     * @param id       идентификатор
     * @param petName  кличка
     * @param petType  тип животного
     * @param color    цвет
     * @param gender   пол
     * @param breed    порода
     */
    @Modifying
    @Query("UPDATE Pet a SET " +
            "a.petType = :pet_type," +
            "a.petName = :pet_name, " +
            "a.color = :color," +
            "a.gender = :gender," +
            "a.breed = :breed" +
            " WHERE a.id = :id")
    void updatePetById(
            @Param("id") Long id,
            @Param("pet_type") PetType petType,
            @Param("pet_name") String petName,
            @Param("color") String color,
            @Param("gender") Gender gender,
            @Param("breed") String breed);

    @Query("SELECT a FROM Pet a WHERE " +
            "a.petType = 0")
    Collection<Pet> findAllDogs();
    @Query("SELECT a FROM Pet a WHERE " +
            "a.petType = 1")
    Collection<Pet> findAllCats();
}
