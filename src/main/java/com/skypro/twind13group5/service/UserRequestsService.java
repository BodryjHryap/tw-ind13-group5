package com.skypro.twind13group5.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.skypro.twind13group5.enums.StatusReport;
import com.skypro.twind13group5.model.Report;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.enums.UserType;
import com.skypro.twind13group5.repository.ReportRepository;
import com.skypro.twind13group5.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserRequestsService {

    private final UserService userService;
    private final PetService petService;
    private final TelegramBot telegramBot;
    private final InlineKeyboardMarkupService inlineKeyboardMarkupService;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ReportService reportService;
    private final Map<Long, Boolean> stateByChatId = new HashMap<>();
    private final Map<Long, Boolean> contactDetailsStateByChatId = new HashMap<>();
    private final Map<Long, Boolean> reportStateByChatId = new HashMap<>();

    public UserRequestsService(UserService userService, PetService petService, TelegramBot telegramBot, InlineKeyboardMarkupService inlineKeyboardMarkupService, UserRepository userRepository, ReportRepository reportRepository, ReportService reportService) {
        this.userService = userService;
        this.petService = petService;
        this.telegramBot = telegramBot;
        this.inlineKeyboardMarkupService = inlineKeyboardMarkupService;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.reportService = reportService;
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
        } else welcomeNotNewUser(chatId, firstName);
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
                case "Расписание, адрес, схема(собаки)" -> getDogShelterInfo2(chatId);
                case "Расписание, адрес, схема(кошки)" -> getCatShelterInfo2(chatId);
                case "Оформить пропуск(собаки)" -> getDogShelterCarPass(chatId);
                case "Оформить пропуск(кошки)" -> getCatShelterCarPass(chatId);
                case "Техника безопасности" -> getSafetyPrecautions(chatId);
                case "Записать телефон" -> writePhone(chatId);
                case "Список собак" -> listOfDogs(chatId);
                case "Правила знакомства" -> datingRules(chatId);
                case "Список документов" -> listOfDocuments(chatId);
                case "Рекомендации" -> getRecommendations(chatId);
                case "Причины отказа" -> getReasonsForRefusal(chatId);
                case "Транспортировка" -> getRecommendationsTransportingPets(chatId);
                case "Дом(щенок)" -> getRecommendationsArrangingHomePuppy(chatId);
                case "Дом(собака)" -> getRecommendationsArrangingHomeDog(chatId);
                case "Дом(инвалид)" -> getRecommendationsArrangingHomeDisabledDog(chatId);
                case "Советы кинолога" -> getDogHandlerAdvice(chatId);
                case "Проверенные кинологи" -> getVerifiedDogHandlers(chatId);


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
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsDogsShelterInfo());
        telegramBot.execute(sendMessage);
    }

    private void getHowAdoptDog(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о том как взять собаку из приюта");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsHowAdoptDog());
        telegramBot.execute(sendMessage);
    }

    private void getDogReport(long chatId) {
        boolean isReport = true;
        reportStateByChatId.put(chatId, isReport);
        SendMessage sendMessage = new SendMessage(chatId, "Здесь будет реализован отчёт о собаке");
        telegramBot.execute(sendMessage);
    }

    private void getCatShelterInfo(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о приюте для кошек");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsCatsShelterInfo());
        telegramBot.execute(sendMessage);
    }

    private void getHowAdoptCat(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о том как взять кошку из приюта");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsCatsShelterInfo());
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

    private void getDogShelterInfo2(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется расписание работы приюта для собак, адрес и схема проезда");
        File file = new File("C:\\Users\\user\\IdeaProjects\\tw-ind13-group5\\driving-directions\\scheme-dog.jpg");
        //путь к файлу схемы проезда
        SendPhoto sendPhoto = new SendPhoto(chatId, file);
        telegramBot.execute(sendMessage);
        telegramBot.execute(sendPhoto);
    }

    private void getCatShelterInfo2(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется расписание работы приюта для кошек, адрес и схема проезда");
        File file = new File("C:\\Users\\user\\IdeaProjects\\tw-ind13-group5\\driving-directions\\scheme-cat.jpg");
        SendPhoto sendPhoto = new SendPhoto(chatId, file);
        telegramBot.execute(sendMessage);
        telegramBot.execute(sendPhoto);
    }

    private void getDogShelterCarPass(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Контактные данные охраны для оформления пропуска на машину. Для приюта для собак");
        telegramBot.execute(sendMessage);
    }

    private void getCatShelterCarPass(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Контактные данные охраны для оформления пропуска на машину. Для приюта для кошек");
        telegramBot.execute(sendMessage);
    }

    private void getSafetyPrecautions(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Общие рекомендации о технике безопасности на территории приюта");
        telegramBot.execute(sendMessage);
    }

    private void writePhone(long chatId) {
        boolean isContactDetails = true;
        contactDetailsStateByChatId.put(chatId, isContactDetails);
        SendMessage sendMessage = new SendMessage(chatId, "Введите номер телефона в формате +7-9**-***-**-**");
        telegramBot.execute(sendMessage);
    }

    public boolean checkContactDetails(Update update) {
        if (update.message() == null)
            return false;
        long chatId = update.message().from().id();
        if (!contactDetailsStateByChatId.containsKey(chatId))
            return false;
        if (contactDetailsStateByChatId.get(chatId)) {
            handleContactDetails(update);
            contactDetailsStateByChatId.remove(chatId);
            return true;
        }
        return false;
    }

    public boolean validateContactDetails(String phone) {
        Pattern patternPhone = Pattern.compile("^(\\+7-)9\\d{2}(-)\\d{3}(-)\\d{2}(-)\\d{2}$");
        Matcher matcher = patternPhone.matcher(phone);
        return matcher.matches();
    }

    private void handleContactDetails(Update update) {
        Message message = update.message();
        Long chatId = message.from().id();
        long userId = update.message().from().id();
        String text = message.text();
        String name = message.from().username();

        if (validateContactDetails(text)) {
            telegramBot.execute(new SendMessage(chatId, "ok"));
            userService.addPhoneNumber(userId, text);
        } else {
            telegramBot.execute(new SendMessage(chatId, "не ok"));
        }
    }

    private void listOfDogs(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Список собак, имеющихся в приюте");
        telegramBot.execute(sendMessage);
        SendMessage sendMessage2 = new SendMessage(chatId, petService.getAllPets().toString());
        telegramBot.execute(sendMessage2);

    }

    private void datingRules(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещаются правила знакомства с животным до того, как забрать его из приюта");
        telegramBot.execute(sendMessage);
    }

    private void listOfDocuments(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещается список документов, необходимых для того, чтобы взять животное из приюта");
        telegramBot.execute(sendMessage);
    }

    private void getRecommendations(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Максимально полная информацию о том, как человеку предстоит подготовиться к встрече с новым членом семьи");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createRecommendationsButtons());
        telegramBot.execute(sendMessage);
    }

    private void getReasonsForRefusal(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещается список причин по которым могли отказать в усыновлении животного");
        telegramBot.execute(sendMessage);
    }

    private void getRecommendationsTransportingPets(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещается список рекомендаций по транспортировке животного");
        telegramBot.execute(sendMessage);
    }

    private void getRecommendationsArrangingHomePuppy(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещается список рекомендаций по обустройству дома для щенка");
        telegramBot.execute(sendMessage);
    }

    private void getRecommendationsArrangingHomeDog(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещается список рекомендаций по обустройству дома для взрослого животного");
        telegramBot.execute(sendMessage);
    }

    private void getRecommendationsArrangingHomeDisabledDog(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещается список рекомендаций по обустройству дома для взрослого животного с ограниченными возможностями (зрение, передвижение)");
        telegramBot.execute(sendMessage);
    }

    private void getDogHandlerAdvice(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь размещаются советы кинолога по первичному общению с собакой");
        telegramBot.execute(sendMessage);
    }
    private void getVerifiedDogHandlers(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь выдаются рекомендации по проверенным кинологам для дальнейшего обращения к ним");
        telegramBot.execute(sendMessage);
    }

    public boolean checkReport(Update update) {

        if (update.message() == null)
            return false;

        long chatId = update.message().from().id();

        if (reportStateByChatId.containsKey(chatId)) {
            handleAdopterReport(update);
            reportStateByChatId.remove(chatId);
            return true;
        }
        return false;
    }

    private void handleAdopterReport(Update update) {

        Message message = update.message();
        Long chatId = message.from().id();
        long userId = update.message().from().id();
        String text = message.text();
        byte[] photo = new byte[0];

        User user = userRepository.findByTelegramId(userId);

//        List<Dialog> dialogList = dialogRepository.findAll().stream().toList();
//        for (Dialog dialog : dialogList) {
//            dialogRepository.delete(dialog);
//        }

        Report report = reportRepository.findReportByUserId(user);
        LocalDate dateReport = LocalDate.now();
        StatusReport statusReport = StatusReport.DEFAULT;

        if (update.message().photo() != null) {
            PhotoSize photoSize = message.photo()[message.photo().length - 1];
            GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
            if (getFileResponse.isOk()) {
                try {
                    photo = telegramBot.getFileContent(getFileResponse.file());
                    sendMessage(chatId, "Фото отправлено");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            sendMessage(chatId, "Текст отправлен");
        }

        if (report == null) {
            reportService.saveReport(user,
                    text,
                    photo,
                    dateReport,
                    statusReport);
        } else {
            reportService.updateReportByUserId(user,
                    text,
                    photo,
                    dateReport,
                    statusReport);
        }

    }
    private void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        telegramBot.execute(sendMessage);
    }
}

