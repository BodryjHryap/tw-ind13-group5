package com.skypro.twind13group5.enums;

/**
 * в enum созданы
 * Типы пользователей:
 * Пользователь, Усыновитель, Волонтер
 */

public enum UserType {
    DEFAULT("Пользователь"),
    //GUEST("Гость"),
    ADOPTER("Усыновитель"),
    VOLUNTEER("Волонтер");
    private final String translationType;

    UserType(String translationType) {
        this.translationType = translationType;
    }

    public String getTranslationColor() {
        return translationType;
    }
}
