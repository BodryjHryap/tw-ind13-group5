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

    public Keyboard createButtonsDogsShelterInfo() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Расписание работы, адрес и схема проезда").callbackData("Расписание, адрес, схема(собаки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оформить пропуск на машину").callbackData("Оформить пропуск(собаки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации о технике безопасности").callbackData("Техника безопасности"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оставить контактные данные для связи").callbackData("Записать телефон"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Приют для собак"));
        return inlineKeyboardMarkup;
    }
    public Keyboard createButtonsCatsShelterInfo() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Расписание работы, адрес и схема проезда").callbackData("Расписание, адрес, схема(кошки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оформить пропуск на машину").callbackData("Оформить пропуск(кошки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации о технике безопасности").callbackData("Техника безопасности"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оставить контактные данные для связи").callbackData("Записать телефон"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Приют для кошек"));
        return inlineKeyboardMarkup;
    }

    public Keyboard createButtonsHowAdoptDog() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Список животных для усыновления").callbackData("Список собак"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Правила знакомства с животным").callbackData("Правила знакомства"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Список документов для усыновления").callbackData("Список документов"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Список рекомендаций").callbackData("Рекомендации"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Возможные причины отказа").callbackData("Причины отказа"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оставить контактные данные для связи").callbackData("Записать телефон"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Приют для собак"));
        return inlineKeyboardMarkup;
    }

    public Keyboard createRecommendationsButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации по транспортировке животного").callbackData("Транспортировка"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации по обустройству дома для щенка").callbackData("Дом(щенок)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации по обустройству дома для взрослого животного").callbackData("Дом(собака)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации по обустройству дома для животного с ограниченными возможностями (зрение, передвижение))").callbackData("Дом(инвалид)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Советы кинолога по первичному общению с собакой").callbackData("Советы кинолога"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Проверенные кинологи").callbackData("Проверенные кинологи"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Как взять собаку"));
        return inlineKeyboardMarkup;
    }
}
