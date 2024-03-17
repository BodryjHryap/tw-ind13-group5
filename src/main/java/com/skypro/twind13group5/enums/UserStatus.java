package com.skypro.twind13group5.enums;

/**
 * Статус для усыновителей.
 */
public enum UserStatus {

    APPROVE("Подтвержденный"),
    BLOCKED("Заблокированный");

    private final String translationStatus;

    UserStatus(String translationStatus) {
        this.translationStatus = translationStatus;
    }

    public String getTranslationColor() {
        return translationStatus;
    }
}
