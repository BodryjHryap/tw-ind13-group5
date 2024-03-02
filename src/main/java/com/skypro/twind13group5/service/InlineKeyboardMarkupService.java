package com.skypro.twind13group5.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.springframework.stereotype.Component;

@Component
public class InlineKeyboardMarkupService {
    public Keyboard createButtonsShelterTypeSelect() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для собак").callbackData("Приют для собак"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для кошек").callbackData("Приют для кошек"));
        return inlineKeyboardMarkup;
    }

    public Keyboard createButtonsDogsShelter() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Узнать информацию о приюте").callbackData("Информация о приюте для собак"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Как взять животное из приюта?").callbackData("Как взять собаку"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Прислать отчет о питомце").callbackData("Отчёт о собаке"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Выбор приюта"));
        return inlineKeyboardMarkup;
    }
    public Keyboard createButtonsCatsShelter() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Узнать информацию о приюте").callbackData("Информация о приюте для кошек"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Как взять животное из приюта?").callbackData("Как взять кошку"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Прислать отчет о питомце").callbackData("Отчёт о кошке"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Выбор приюта"));
        return inlineKeyboardMarkup;
    }
}
