package com.skypro.twind13group5.model;

import com.skypro.twind13group5.enums.Gender;
import com.skypro.twind13group5.enums.PetType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Класс животных, в котором передает Id, имя животного, тип животного
 * цвет, пол и породу
 */

@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    public void setPetName(String petName) {
        if (petName == null || petName.isEmpty() || petName.isBlank()) {
            throw new RuntimeException("Имя животного введено некорректно!");
        } else {
            this.petName = petName;
        }
    }

    public void setPetType(PetType petType) {
        if (petType == null) {
            throw new RuntimeException("Тип животного введен некорректно!");
        } else {
            this.petType = petType;
        }
    }

    public void setColor(String color) {
        if (color == null) {
            throw new RuntimeException("Цвет животного введен некорректно!");
        } else {
            this.color = color;
        }
    }

    public void setGender(Gender gender) {
        if (gender == null) {
            throw new RuntimeException("Пол животного введен некорректно!");
        } else {
            this.gender = gender;
        }
    }
}
