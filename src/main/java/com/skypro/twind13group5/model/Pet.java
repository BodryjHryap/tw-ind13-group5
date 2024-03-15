package com.skypro.twind13group5.model;

import com.skypro.twind13group5.enums.Gender;
import com.skypro.twind13group5.enums.PetType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "pet_type")
    private PetType petType;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "color")
    private String color;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "breed")
    private String breed;

    public Pet(PetType petType, String petName, String color, Gender gender, String breed) {
        this.petType = petType;
        this.petName = petName;
        this.color = color;
        this.gender = gender;
        this.breed = breed;
    }

    public Pet() {
    }
}
