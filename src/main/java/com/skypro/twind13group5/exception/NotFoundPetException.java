package com.skypro.twind13group5.exception;

/**
 * Ошибка при отсутствии животного у усыновителя.
 */
public class NotFoundPetException extends RuntimeException {
    public NotFoundPetException(String message) {
        super("У Вас нет животного. Обратитесь к волонтеру.");
    }
}
