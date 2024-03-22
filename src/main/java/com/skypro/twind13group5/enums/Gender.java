package com.skypro.twind13group5.enums;

import lombok.Getter;

/**
 * в enum созданы
 * пол животных:
 * мальчик, девочка
 */
@Getter
public enum Gender {
    MALE("мужской"),
    FEMALE("женский");

    private final String translationGender;

    Gender(String translationGender) {
        this.translationGender = translationGender;}

}
