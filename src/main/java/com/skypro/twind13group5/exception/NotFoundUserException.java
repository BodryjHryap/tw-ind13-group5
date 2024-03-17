package com.skypro.twind13group5.exception;

/**
 * Ошибка по поиску пользователя в БД.
 */
public class NotFoundUserException extends RuntimeException{

    public NotFoundUserException(String message) {
        super("Пользователь не найден в БД!");
    }
}
