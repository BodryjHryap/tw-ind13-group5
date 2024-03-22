package com.skypro.twind13group5.enums;

/**
 * В enum созданы
 * Типы пользователей:
 * Пользователь, Усыновитель, Волонтер
 */

public enum UserType {
    DEFAULT("Пользователь"),
    ADOPTER("Усыновитель"),
    VOLUNTEER("Волонтер");
    private final String translationType;

    UserType(String translationType) {
        this.translationType = translationType;
    }
}
