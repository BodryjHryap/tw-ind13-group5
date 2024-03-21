package com.skypro.twind13group5.enums;

import lombok.Getter;

/**
 * Статус для усыновителей.
 */
@Getter
public enum UserStatus {

    APPROVE("Подтвержденный"),
    BLOCKED("Заблокированный");

    private final String translationStatus;

    UserStatus(String translationStatus) {
        this.translationStatus = translationStatus;
    }

}
