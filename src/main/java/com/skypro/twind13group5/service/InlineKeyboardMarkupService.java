package com.skypro.twind13group5.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Service;

/**
 * Сервис и безнес-логика по созданию привязанных кнопок под сообщениями.
 */

@Service
public class InlineKeyboardMarkupService {
    public InlineKeyboardMarkup createButtonsShelterTypeSelect() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для собак").callbackData("Приют для собак"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для кошек").callbackData("Приют для кошек"));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsDogsShelter() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Узнать информацию о приюте").callbackData("Информация о приюте для собак"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Как взять животное из приюта?").callbackData("Как взять собаку"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Прислать отчет о питомце").callbackData("Прислать отчёт"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Выбор приюта"));
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup createButtonsCatsShelter() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Узнать информацию о приюте").callbackData("Информация о приюте для кошек"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Как взять животное из приюта?").callbackData("Как взять кошку"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Прислать отчет о питомце").callbackData("Прислать отчёт"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Выбор приюта"));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsDogsShelterInfo() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Расписание работы, адрес и схема проезда").callbackData("Расписание, адрес, схема(собаки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оформить пропуск на машину").callbackData("Оформить пропуск(собаки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации о технике безопасности").callbackData("Техника безопасности"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оставить контактные данные для связи").callbackData("Записать телефон"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Приют для собак"));
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup createButtonsCatsShelterInfo() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Расписание работы, адрес и схема проезда").callbackData("Расписание, адрес, схема(кошки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оформить пропуск на машину").callbackData("Оформить пропуск(кошки)"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Рекомендации о технике безопасности").callbackData("Техника безопасности"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Оставить контактные данные для связи").callbackData("Записать телефон"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера").callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню").callbackData("Приют для кошек"));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsHowAdoptDog() {
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

    public InlineKeyboardMarkup createRecommendationsButtons() {
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

    public InlineKeyboardMarkup createButtonsReport() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Правила предоставления отчета")
                .callbackData("Правила предоставления отчёта"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Отчитаться")
                .callbackData("Отчёт"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Позвать волонтера")
                .callbackData("Вызов волонтёра"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Вернуться в предыдущее меню")
                .callbackData("Выбор приюта"));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsCheckReport() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Свободное сообщение")
                .callbackData("CLICK_FREE_MESSAGE"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Отчет в порядке")
                .callbackData("CLICK_OK"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Есть проблемы с отчетом")
                .callbackData("CLICK_NOT_OK"));

        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup createButtonsCheckReportNotOk() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Предупредить усыновителя")
                .callbackData("CLICK_WARNING_REPORT"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Продлить испытательный срок")
                .callbackData("CLICK_EXTEND"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Испытательный срок не пройден")
                .callbackData("CLICK_DELETE_ADOPTER"));

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsVolunteerMenu() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Меню пользователя")
                .callbackData("Выбор приюта"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Добавить усыновителя")
                .callbackData("Добавить усыновителя"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Проверить отчет")
                .callbackData("CLICK_CHECK_REPORT"));

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createButtonsCheckReportNotOkExtend() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("На 14 дней")
                .callbackData("CLICK_EXTEND_14_DAY"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("На 30 дней")
                .callbackData("CLICK_EXTEND_30_DAY"));

        return inlineKeyboardMarkup;
    }
}
