package com.skypro.twind13group5.services;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.skypro.twind13group5.service.InlineKeyboardMarkupService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InlineKeyboardMarkupServiceTest {

    @Test
    public void testCreateButtonsShelterTypeSelect() {
        InlineKeyboardMarkupService service = new InlineKeyboardMarkupService();
        InlineKeyboardMarkup markup = service.createButtonsShelterTypeSelect();
        assertNotNull(markup);
        assertEquals(2, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Приют для собак", button1.text());
        assertEquals("Приют для собак", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Приют для кошек", button2.text());
        assertEquals("Приют для кошек", button2.callbackData());
    }

    @Test
    public void testCreateButtonsDogsShelter() {
        InlineKeyboardMarkupService service = new InlineKeyboardMarkupService();
        InlineKeyboardMarkup markup = service.createButtonsDogsShelter();
        assertNotNull(markup);
        assertEquals(5, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Узнать информацию о приюте", button1.text());
        assertEquals("Информация о приюте для собак", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Как взять животное из приюта?", button2.text());
        assertEquals("Как взять собаку", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Прислать отчет о питомце", button3.text());
        assertEquals("Отчёт о собаке", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Позвать волонтера", button4.text());
        assertEquals("Вызов волонтёра", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Вернуться в предыдущее меню", button5.text());
        assertEquals("Выбор приюта", button5.callbackData());
    }
    @Test
    public void testCreateButtonsCatsShelter() {
        InlineKeyboardMarkupService service = new InlineKeyboardMarkupService();
        InlineKeyboardMarkup markup = service.createButtonsCatsShelter();
        assertNotNull(markup);
        assertEquals(5, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Узнать информацию о приюте", button1.text());
        assertEquals("Информация о приюте для кошек", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Как взять животное из приюта?", button2.text());
        assertEquals("Как взять кошку", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Прислать отчет о питомце", button3.text());
        assertEquals("Отчёт о кошке", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Позвать волонтера", button4.text());
        assertEquals("Вызов волонтёра", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Вернуться в предыдущее меню", button5.text());
        assertEquals("Выбор приюта", button5.callbackData());
    }

    @Test
    public void testCreateButtonsDogsShelterInfo() {
        InlineKeyboardMarkupService service = new InlineKeyboardMarkupService();
        InlineKeyboardMarkup markup = service.createButtonsDogsShelterInfo();
        assertNotNull(markup);
        assertEquals(6, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Расписание работы, адрес и схема проезда", button1.text());
        assertEquals("Расписание, адрес, схема(собаки)", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Оформить пропуск на машину", button2.text());
        assertEquals("Оформить пропуск(собаки)", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Рекомендации о технике безопасности", button3.text());
        assertEquals("Техника безопасности", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Оставить контактные данные для связи", button4.text());
        assertEquals("Записать телефон", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Позвать волонтера", button5.text());
        assertEquals("Вызов волонтёра", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[5][0];
        assertNotNull(button6);
        assertEquals("Вернуться в предыдущее меню", button6.text());
        assertEquals("Приют для собак", button6.callbackData());
    }
    @Test
    public void testCreateButtonsCatsShelterInfo() {
        InlineKeyboardMarkupService service = new InlineKeyboardMarkupService();
        InlineKeyboardMarkup markup = service.createButtonsCatsShelterInfo();
        assertNotNull(markup);
        assertEquals(6, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Расписание работы, адрес и схема проезда", button1.text());
        assertEquals("Расписание, адрес, схема(кошки)", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Оформить пропуск на машину", button2.text());
        assertEquals("Оформить пропуск(кошки)", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Рекомендации о технике безопасности", button3.text());
        assertEquals("Техника безопасности", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Оставить контактные данные для связи", button4.text());
        assertEquals("Записать телефон", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Позвать волонтера", button5.text());
        assertEquals("Вызов волонтёра", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[5][0];
        assertNotNull(button6);
        assertEquals("Вернуться в предыдущее меню", button6.text());
        assertEquals("Приют для кошек", button6.callbackData());

    }

    @Test
    public void testCreateButtonsHowAdoptDog() {
        InlineKeyboardMarkupService service = new InlineKeyboardMarkupService();
        InlineKeyboardMarkup markup = service.createButtonsHowAdoptDog();
        assertNotNull(markup);
        assertEquals(8, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Список животных для усыновления", button1.text());
        assertEquals("Список собак", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Правила знакомства с животным", button2.text());
        assertEquals("Правила знакомства", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Список документов для усыновления", button3.text());
        assertEquals("Список документов", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Список рекомендаций", button4.text());
        assertEquals("Рекомендации", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Возможные причины отказа", button5.text());
        assertEquals("Причины отказа", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[5][0];
        assertNotNull(button6);
        assertEquals("Оставить контактные данные для связи", button6.text());
        assertEquals("Записать телефон", button6.callbackData());

        InlineKeyboardButton button7 = markup.inlineKeyboard()[6][0];
        assertNotNull(button7);
        assertEquals("Позвать волонтера", button7.text());
        assertEquals("Вызов волонтёра", button7.callbackData());

        InlineKeyboardButton button8 = markup.inlineKeyboard()[7][0];
        assertNotNull(button8);
        assertEquals("Вернуться в предыдущее меню", button8.text());
        assertEquals("Приют для собак", button8.callbackData());
    }

    @Test
    public void testCreateRecommendationsButtons() {
        InlineKeyboardMarkupService service = new InlineKeyboardMarkupService();
        InlineKeyboardMarkup markup = service.createRecommendationsButtons();
        assertNotNull(markup);
        assertEquals(7, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Рекомендации по транспортировке животного", button1.text());
        assertEquals("Транспортировка", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Рекомендации по обустройству дома для щенка", button2.text());
        assertEquals("Дом(щенок)", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Рекомендации по обустройству дома для взрослого животного", button3.text());
        assertEquals("Дом(собака)", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Рекомендации по обустройству дома для животного с ограниченными возможностями (зрение, передвижение))", button4.text());
        assertEquals("Дом(инвалид)", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Советы кинолога по первичному общению с собакой", button5.text());
        assertEquals("Советы кинолога", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[5][0];
        assertNotNull(button6);
        assertEquals("Проверенные кинологи", button6.text());
        assertEquals("Проверенные кинологи", button6.callbackData());

        InlineKeyboardButton button7 = markup.inlineKeyboard()[6][0];
        assertNotNull(button7);
        assertEquals("Вернуться в предыдущее меню", button7.text());
        assertEquals("Как взять собаку", button7.callbackData());
    }
}
