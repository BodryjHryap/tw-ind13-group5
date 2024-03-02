package com.skypro.twind13group5.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.enums.UserType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRequestsService {

    private final UserService userService;
    private final TelegramBot telegramBot;
    private final InlineKeyboardMarkupService inlineKeyboardMarkupService;
    private final Map<Long, Boolean> stateByChatId = new HashMap<>();

    public UserRequestsService(UserService userService, TelegramBot telegramBot, InlineKeyboardMarkupService inlineKeyboardMarkupService) {
        this.userService = userService;
        this.telegramBot = telegramBot;
        this.inlineKeyboardMarkupService = inlineKeyboardMarkupService;
    }

    public void sendMessageStart(Update update) {

        Message message = update.message();
        Long chatId = message.from().id();
        String text = message.text();
        String firstName = update.message().from().firstName();
        String userName = update.message().from().username();
        long telegramId = update.message().from().id();

        if ("/start".equals(text)) {

            User user = userService.findUserByTelegramId(telegramId);

            if (user == null) {
                welcomeNewUser(chatId, firstName);
                userService.addUser(telegramId, userName, UserType.DEFAULT);
            } else {
                welcomeNotNewUser(chatId, firstName);
            }
        }
    }

    private void welcomeNewUser(Long chatId, String firstName) {
        String helloText = String.format("Привет, %s! \nЭтот бот помогает взаимодействовать с приютами.", firstName);
        SendMessage sendMessage = new SendMessage(chatId, helloText);
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsShelterTypeSelect());
        telegramBot.execute(sendMessage);
    }

    private void welcomeNotNewUser(Long chatId, String firstName) {
        String helloText = String.format("С возвращением, %s! \nВыбери приют, который тебя интересует", firstName);
        SendMessage sendMessage = new SendMessage(chatId, helloText);
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsShelterTypeSelect());
        telegramBot.execute(sendMessage);
    }

    public void createButton(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            String data = callbackQuery.data();
            switch (data) {
                case "Приют для собак" -> getDogShelter(chatId);
                case "Приют для кошек" -> getCatShelter(chatId);
                case "Выбор приюта" -> getChoosingShelter(chatId);
                case "Информация о приюте для собак" -> getDogShelterInfo(chatId);
                case "Как взять собаку" -> getHowAdoptDog(chatId);
                case "Отчёт о собаке" -> getDogReport(chatId);
                case "Информация о приюте для кошек" -> getCatShelterInfo(chatId);
                case "Как взять кошку" -> getHowAdoptCat(chatId);
                case "Отчёт о кошке" -> getCatReport(chatId);
                case "Вызов волонтёра" -> callVolunteer(chatId);

            }
        }
    }

    private void getDogShelter(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для собак");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsDogsShelter());
        telegramBot.execute(sendMessage);
    }

    private void getCatShelter(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для кошек");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsCatsShelter());
        telegramBot.execute(sendMessage);
    }

    private void getChoosingShelter(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Выбери приют, который тебя интересует");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsShelterTypeSelect());
        telegramBot.execute(sendMessage);
    }

    private void getDogShelterInfo(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о приюте для собак");
        telegramBot.execute(sendMessage);
    }

    private void getHowAdoptDog(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о том как взять собаку из приюта");
        telegramBot.execute(sendMessage);
    }

    private void getDogReport(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь будет реализован отчёт о собаке");
        telegramBot.execute(sendMessage);
    }

    private void getCatShelterInfo(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о приюте для кошек");
        telegramBot.execute(sendMessage);
    }
    private void getHowAdoptCat(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о том как взять кошку из приюта");
        telegramBot.execute(sendMessage);
    }

    private void getCatReport(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь будет реализован отчёт о кошке");
        telegramBot.execute(sendMessage);
    }

    private void callVolunteer(long chatId) {
        boolean forVolunteer = true;
        stateByChatId.put(chatId, forVolunteer);
        SendMessage sendMessage = new SendMessage(chatId, "Напиши сообщение для волонтера");
        telegramBot.execute(sendMessage);
    }

    public boolean checkVolunteer(Update update) {

        if (update.message() == null)
            return false;

        long chatId = update.message().from().id();

        if (!stateByChatId.containsKey(chatId))
            return false;

        if (stateByChatId.get(chatId)) {
            handleCallVolunteer(update);
            stateByChatId.remove(chatId);
            return true;
        }
        return false;
    }

    private void handleCallVolunteer(Update update) {
        Message message = update.message();
        Long chatId = message.from().id();
        long userId = update.message().from().id();
        String text = message.text();
        String name = message.from().username();


        List<User> users = userService.getAllUsers();
        List<User> volunteers = new ArrayList<User>();
        for (User user : users) {
            if (user.getUserType() == UserType.VOLUNTEER) {
                volunteers.add(user);
            }
        }

        for (User volunteer : volunteers) {
            telegramBot.execute(new SendMessage(volunteer.getTelegramId(), "Усыновитель " +
                    "" + '@' + name + " послал сообщение: " + text));
        }

        SendMessage message1 = new SendMessage(chatId, "Первый освободившийся волонтёр ответит вам в ближайшее время");
        telegramBot.execute(message1);
    }
}
