package com.skypro.twind13group5.exception;

/**
 * Ошибка по поиску усыновителя в БД.
 */
public class NotFoundAdopterException extends RuntimeException{

    public NotFoundAdopterException(String message) {
        super("Усыновитель не найден в БД!");
    }
}