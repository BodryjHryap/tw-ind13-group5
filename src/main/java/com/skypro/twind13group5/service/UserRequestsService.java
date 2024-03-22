package com.skypro.twind13group5.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.skypro.twind13group5.enums.ShelterType;
import com.skypro.twind13group5.enums.StatusReport;
import com.skypro.twind13group5.enums.UserStatus;
import com.skypro.twind13group5.exception.NotFoundReportException;
import com.skypro.twind13group5.listener.TelegramBotUpdateListener;
import com.skypro.twind13group5.model.*;
import com.skypro.twind13group5.enums.UserType;
import com.skypro.twind13group5.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис и бизнес-логика по работе запросов от пользователей.
 */

@Service
public class UserRequestsService {

    private static Report checkReport;

    private static String textReport;
    private static byte[] picture;
    private final Pattern patternAdopter = Pattern
            .compile("(^\\d{9,10})\\s+(\\d)\\s+(\\d+$)");//ALT+Enter -> check
    private final Pattern pattern = Pattern
            .compile("(^[А-я]+)\\s+([А-я]+)\\s+(\\d{10})\\s+([А-я0-9\\d]+$)");//ALT+Enter -> check
    private final UserRepository userRepository;
    private final DialogRepository dialogRepository;
    private final ReportRepository reportRepository;
    private final PetRepository petRepository;
    private final AdopterRepository adopterRepository;
    private final UserService userService;
    private final PetService petService;
    private final AdopterService adopterService;
    private final ReportService reportService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);
    private final TelegramBot telegramBot;
    private final InlineKeyboardMarkupService inlineKeyboardMarkupService;

    final Map<Long, UserType> reportStateByChatId = new HashMap<>();
    private final Map<Long, Boolean> stateByChatId = new HashMap<>();
    private final Map<Long, Boolean> contactDetailsStateByChatId = new HashMap<>();
    final Map<Long, UserType> adopterStateByChatId = new HashMap<>();
    private final Map<Long, UserType> messageStateByChatId = new HashMap<>();
    private static final int HOUR_OF_DAY = 18;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;
    private static final long PERIOD_SECONDS = 24 * 60 * 60;

    public UserRequestsService(UserRepository userRepository, DialogRepository dialogRepository, ReportRepository reportRepository, PetRepository petRepository, AdopterRepository adopterRepository, UserService userService, PetService petService, ReportService reportService, TelegramBot telegramBot, InlineKeyboardMarkupService inlineKeyboardMarkupService, AdopterService adopterService) {
        this.userRepository = userRepository;
        this.dialogRepository = dialogRepository;
        this.reportRepository = reportRepository;
        this.petRepository = petRepository;
        this.adopterRepository = adopterRepository;
        this.userService = userService;
        this.petService = petService;
        this.reportService = reportService;
        this.telegramBot = telegramBot;
        this.inlineKeyboardMarkupService = inlineKeyboardMarkupService;
        this.adopterService = adopterService;
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
                userService.addUser(telegramId, userName, UserType.DEFAULT, UserStatus.APPROVE);

            } else if (user.getUserType() == UserType.DEFAULT && user.getUserStatus() == UserStatus.APPROVE) {
                welcomeNotNewUser(chatId, firstName);

            } else if (user.getUserType() == UserType.ADOPTER && user.getUserStatus() == UserStatus.APPROVE) {

                Report report = reportRepository.findReportByUserId(user);
                if (report.getDateEndOfProbation() == LocalDate.now()) {
                    sendMessage(chatId, "Поздравляем, Вы прошли испытательный срок!");
                    userRepository.delete(user);
                }

                if (user.getShelterType() == ShelterType.CAT_SHELTER) {
                    List<Dialog> dialogList = dialogRepository.findAll().stream().toList();

                    for (Dialog dialog : dialogList) {
                        if (dialog.getGuestId().equals(user)) {
                            sendMessage(chatId, dialog.getTextMessage());
                        }
                    }
                    welcomeAdopterCatShelter(chatId, firstName);

                } else if (user.getShelterType() == ShelterType.DOG_SHELTER) {
                    List<Dialog> dialogList = dialogRepository.findAll().stream().toList();

                    for (Dialog dialog : dialogList) {
                        if (dialog.getGuestId().equals(user)) {
                            sendMessage(chatId, dialog.getTextMessage());
                        }
                    }
                    welcomeAdopterDogShelter(chatId, firstName);
                }

            } else if (user.getUserType() == UserType.VOLUNTEER && user.getUserStatus() == UserStatus.APPROVE) {
                welcomeVolunteer(chatId, firstName);

            } else {
                blockedUser(chatId, firstName);
                List<Dialog> dialogList = dialogRepository.findAll().stream().toList();

                for (Dialog dialog : dialogList) {
                    if (dialog.getGuestId().equals(user)) {
                        sendMessage(chatId, dialog.getTextMessage());
                    }
                }
            }
        }
    }

    private void welcomeVolunteer(long chatId, String name) {

        SendMessage sendMessage =
                new SendMessage(chatId, String.format("Здравствуйте, %s!\n" +
                        "Вы зашли в меню волонтеров!\n" +
                        "Выберете, пожалуйста, вариант из предложенного меню!", name));

        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsVolunteerMenu());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private void welcomeAdopterDogShelter(long chatId, String name) {

        SendMessage sendMessage =
                new SendMessage(chatId, String.format("Здравствуйте, %s!\n" +
                        "Поздравляем, Вы стали усыновителем питомца нашего приюта!\n" +
                        "Чтобы пройти испытательный срок, Вы должны соблюдать правила" +
                        "и вовремя отправлять отчеты! Желаем удачи!\n" +
                        "Вы выбрали приют для собак!\n" +
                        "Выберете, пожалуйста, вариант из предложенного меню!", name));

        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsReport());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private void welcomeAdopterCatShelter(long chatId, String name) {

        SendMessage sendMessage =
                new SendMessage(chatId, String.format("Здравствуйте, %s!\n" +
                        "Поздравляем, Вы стали усыновителем питомца нашего приюта!\n" +
                        "Чтобы пройти испытательный срок, Вы должны соблюдать правила" +
                        "и вовремя отправлять отчеты! Желаем удачи!\n" +
                        "Вы выбрали приют для кошек!\n" +
                        "Выберете, пожалуйста, вариант из предложенного меню!", name));

        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsReport());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private void blockedUser(long chatId, String name) {

        SendMessage sendMessage =
                new SendMessage(chatId, String.format("Здравствуйте, %s!\n" +
                        "Вам отказано в доступе!", name));

        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
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

    public boolean checkAdopter(Update update) {

        if (update.message() == null)
            return false;

        long chatId = update.message().from().id();

        if (adopterStateByChatId.containsKey(chatId)) {
            recordingNewAnimals(update);
            adopterStateByChatId.remove(chatId);
                      return true;
        }
        return false;
    }

    public void createButton(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            String data = callbackQuery.data();
            sendingTimer(chatId);
            switch (data) {
                case "Приют для собак" -> getDogShelter(chatId);
                case "Приют для кошек" -> getCatShelter(chatId);
                case "Выбор приюта" -> getChoosingShelter(chatId);
                case "Информация о приюте для собак" -> getDogShelterInfo(chatId);
                case "Как взять собаку" -> getHowAdoptDog(chatId);
                case "Прислать отчёт" -> getReport(chatId);
                case "Информация о приюте для кошек" -> getCatShelterInfo(chatId);
                case "Как взять кошку" -> getHowAdoptCat(chatId);
                case "Вызов волонтёра" -> callVolunteer(chatId);
                case "Расписание, адрес, схема(собаки)" -> getDogShelterInfo2(chatId);
                case "Расписание, адрес, схема(кошки)" -> getCatShelterInfo2(chatId);
                case "Оформить пропуск(собаки)" -> getDogShelterCarPass(chatId);
                case "Оформить пропуск(кошки)" -> getCatShelterCarPass(chatId);
                case "Техника безопасности" -> getSafetyPrecautions(chatId);
                case "Записать телефон" -> writePhone(chatId);
                case "Список собак" -> listOfDogs(chatId);
                case "Список кошек" -> listOfCats(chatId);
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

                case "Правила предоставления отчёта" -> {sendMessage(chatId, "После того как новый усыновитель забрал животное из приюта, " +
                        "он обязан в течение месяца присылать информацию о том, как животное чувствует " +
                        "себя на новом месте. В ежедневный отчет входит следующая информация:\n" +
                        "    - Фото животного.\n" +
                        "    - Рацион животного.\n" +
                        "    - Общее самочувствие и привыкание к новому месту.\n" +
                        "    - Изменение в поведении: отказ от старых привычек, приобретение новых.\n" +
                        "Отчет нужно присылать каждый день, ограничений в сутках по времени сдачи отчета нет. " +
                        "Каждый день волонтеры осматривают все присланные отчеты после 21:00. ");

                }
                case "Отчёт" ->{reportStateByChatId.put(chatId, UserType.ADOPTER);
                    SendMessage sendMessage1 = new SendMessage(chatId, """
                            Отправьте отчет о питомце двумя сообщениями:
                            Фото питомца и текстовое сообщение, в которое будет включено:
                            рацион питомца, общее самочувствие и привыкание к новому месту,
                            а так же изменение в поведении.""");
                    sendMessage(sendMessage1);}

                case "Добавить усыновителя" -> {

                    SendMessage sendMessage = new SendMessage(chatId, """
                            Чтобы записать усыновителя, нужно заполнить форму:
                            Напишите telegramId усыновителя, petId, shelterId
                            в формате: 123456789 1 1""");
                    adopterStateByChatId.put(chatId, UserType.ADOPTER);
                    sendMessage(sendMessage);
                }
                case "CLICK_OK" -> {checkReportStatusOk();
                    sendMessage(chatId, "Отчет принят!");
                    }
                case "CLICK_CHECK_REPORT" -> getCheckReport(update);
                case "CLICK_FREE_MESSAGE" ->{messageStateByChatId.put(chatId, UserType.VOLUNTEER);
                    getFreeMessage(update);}
                case "CLICK_NOT_OK" -> {checkReportStatusNotOk();
                    SendMessage sendMessage2 = new SendMessage(chatId, "Отчет не принят!");
                    sendMessage2.replyMarkup(inlineKeyboardMarkupService.createButtonsCheckReportNotOk());
                    sendMessage(sendMessage2);}
                case "CLICK_WARNING_REPORT" -> {sendWarningMessage(update);
                    sendMessage(chatId, "Предупреждение отправлено!");}
                case "CLICK_DELETE_ADOPTER" -> {sendWarningDeleteAdopter(update);
                    sendMessage(chatId, "Пользователь заблокирован!");}
                case "CLICK_EXTEND" -> {SendMessage sendMessage3 = new SendMessage(chatId, "На сколько продлить испытательный срок?");
                    sendMessage3.replyMarkup(inlineKeyboardMarkupService.createButtonsCheckReportNotOkExtend());
                    sendMessage(sendMessage3);}
                case "CLICK_EXTEND_14_DAY" -> {sendExtend14Day();
                    sendMessage(chatId, "Испытательный срок продлен на 14 дней!");}
                case "CLICK_EXTEND_30_DAY" -> {sendExtend30Day();
                    sendMessage(chatId, "Испытательный срок продлен на 30 дней!");}
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

    private void getReport(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Отчёт");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsReport());
        telegramBot.execute(sendMessage);
    }

    private void getCatShelterInfo(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о приюте для кошек");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsCatsShelterInfo());
        telegramBot.execute(sendMessage);
    }

    private void getHowAdoptCat(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здесь пишется информация о том как взять кошку из приюта");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsHowAdoptCat());
        telegramBot.execute(sendMessage);
    }

    private void callVolunteer(long chatId) {
        boolean forVolunteer = true;
        stateByChatId.put(chatId, forVolunteer);
        SendMessage sendMessage = new SendMessage(chatId, "Оставьте сообщение для волонтера");
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

        if (validateContactDetails(text)) {
            telegramBot.execute(new SendMessage(chatId, "Ваш телефон успешно сохранен!"));
            userService.addPhoneNumber(userId, text);
        } else {
            telegramBot.execute(new SendMessage(chatId, "Ваш телефон не удалось записать, попробуйте еще раз."));
        }
    }

    private void listOfDogs(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Список собак, имеющихся в приюте");
        telegramBot.execute(sendMessage);
        SendMessage sendMessage2 = new SendMessage(chatId, petService.getAllDogs().toString());
        telegramBot.execute(sendMessage2);
    }

    private void listOfCats(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Список кошек, имеющихся в приюте");
        telegramBot.execute(sendMessage);
        SendMessage sendMessage2 = new SendMessage(chatId, petService.getAllCats().toString());
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

    private void handleAdopterReport(Update update) {

        Message message = update.message();
        Long chatId = message.from().id();
        long userId = update.message().from().id();
        String text = message.text();

        User user1 = userRepository.findByTelegramId(userId);

        List<Dialog> dialogList = dialogRepository.findAll().stream().toList();
        for (Dialog dialog : dialogList) {
            dialogRepository.delete(dialog);
        }

        String name = message.from().username();
        List<User> users = userService.getAllUsers();
        List<User> volunteers = new ArrayList<User>();

        Report report = reportRepository.findReportByUserId(user1);
        LocalDate dateReport = LocalDate.now();
        StatusReport statusReport = StatusReport.DEFAULT;

        if (update.message().photo() == null && text != null) {
            textReport = text;
            sendMessage(chatId, "текст заполнен!");
        } else if (update.message().photo() != null && text == null) {
            PhotoSize photoSize = message.photo()[message.photo().length - 1];
            GetFileResponse getFileResponse = telegramBot.execute(
                    new GetFile(photoSize.fileId()));

            if (getFileResponse.isOk()) {
                try {
                    picture = telegramBot.getFileContent(getFileResponse.file());
                    sendMessage(chatId, "фото отправлено!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (report == null) {
            reportService.saveReport(user1,
                    dateReport,
                    statusReport,
                    textReport,
                    picture);
        } else {
            reportService.updateReportByUserId(user1,
                    dateReport,
                    statusReport,
                    textReport,
                    picture);
        }

        SendPhoto sendPhoto = new SendPhoto(chatId, picture);

        if (textReport != null && picture != null) {
            SendMessage message1 = new SendMessage(chatId, "Спасибо за полный отчёт," +
                    " результат проверки узнаете в течение дня!");
            for (User user : users) {
                if (user.getUserType() == UserType.VOLUNTEER) {
                    volunteers.add(user);
                }
            }
            for (User volunteer : volunteers) {
                telegramBot.execute(new SendMessage(volunteer.getTelegramId(), "Усыновитель " +
                        "" + '@' + name + " прислал проверенный ботом отчет: " + textReport));
                telegramBot.execute(sendPhoto);
            }
            message1.replyMarkup(inlineKeyboardMarkupService.createButtonsReport());
            telegramBot.execute(message1);

        } else if (textReport == null || picture == null) {
            SendMessage message2 = new SendMessage(chatId, "Спасибо за отчёт! К сожалению, он не полный, " +
                    "поэтому мы настоятельно рекомендуем прислать полный отчет, чтобы избежать последствий!");
            message2.replyMarkup(inlineKeyboardMarkupService.createButtonsReport());
            telegramBot.execute(message2);
        }
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

    private void sendExtend30Day() {

        User user = checkReport.getUserId();

        LocalDate dateEndOfProbation = LocalDate.now().plusMonths(1);

        String textMessage = "Испытательный срок продлен на 30 дней!";

        reportService.updateDateEndOfProbationById(user,
                dateEndOfProbation);

        long chatId = checkReport.getUserId().getTelegramId();
        telegramBot.execute(new SendMessage(chatId, textMessage));

        Dialog dialog = new Dialog(user, null, textMessage, LocalDate.now());

        dialogRepository.save(dialog);
    }

    private void sendExtend14Day() {

        User user = checkReport.getUserId();

        LocalDate dateEndOfProbation = LocalDate.now().plusWeeks(2);

        String textMessage = "Испытательный срок продлен на 14 дней!";

        reportService.updateDateEndOfProbationById(user,
                dateEndOfProbation);

        long chatId = checkReport.getUserId().getTelegramId();
        telegramBot.execute(new SendMessage(chatId, textMessage));

        Dialog dialog = new Dialog(user, null, textMessage, LocalDate.now());



        dialogRepository.save(dialog);
    }

    private void sendWarningDeleteAdopter(Update update) {

        String textMessage = "Вы не прошли испытательный срок, " +
                "в ближайшее время с Вами свяжется волонтер для " +
                "дальнейшего плана действия!" +
                "Отныне, Вы персона нон града и доступ в наш приют заблокирован!";

        LocalDate date = LocalDate.now();

        User guest = checkReport.getUserId();
        long chatId = checkReport.getUserId().getTelegramId();
        telegramBot.execute(new SendMessage(chatId, textMessage));
        UserStatus userStatus = UserStatus.BLOCKED;

        Dialog dialog = new Dialog(guest, null, textMessage, date);

        dialogRepository.save(dialog);

        userService.updateStatusUserById(guest.getTelegramId(), userStatus);
    }

    private void sendWarningMessage(Update update) {
        Message message = update.callbackQuery().message();
        String textMessage = "Дорогой усыновитель, мы заметили, " +
                "что ты заполняешь отчет не так подробно, как необходимо." +
                " Пожалуйста, подойди ответственнее к этому занятию. " +
                "В противном случае волонтеры приюта будут обязаны самолично " +
                "проверять условия содержания животного";

        long userId = message.from().id();
        long chatId = checkReport.getUserId().getTelegramId();
        telegramBot.execute(new SendMessage(chatId, textMessage));

        LocalDate date = LocalDate.now();

        User guest = checkReport.getUserId();
        User volunteer = userService.findUserByTelegramId(userId);

        Dialog dialog = new Dialog(guest, volunteer, textMessage, date);

        dialogRepository.save(dialog);
    }

    private void checkReportStatusOk() {
        String textMessage = "Отчет принят волонтером!";
        long chatId = checkReport.getUserId().getTelegramId();

        User user = checkReport.getUserId();

        StatusReport statusReport = StatusReport.ACCEPTED;

        LocalDate dateEndOfProbation = null;

        reportService.updateDateEndOfProbationById(user,
                dateEndOfProbation);

        telegramBot.execute(new SendMessage(chatId, textMessage));
        reportService.updateStatusReportById(user,
                statusReport);

    }

    private void checkReportStatusNotOk() {
        String textMessage = "Отчет не принят волонтером!";
        long chatId = checkReport.getUserId().getTelegramId();

        User user = checkReport.getUserId();

        StatusReport statusReport = StatusReport.NOT_ACCEPTED;

        telegramBot.execute(new SendMessage(chatId, textMessage));
        reportService.updateStatusReportById(user,
                statusReport);
    }

    public void getFreeMessage(Update update) {

        Message message = update.callbackQuery().message();
        long chatId = message.chat().id();

        User guest = checkReport.getUserId();

        sendMessage(chatId, "Напишите пользователю " + '@' + guest.getTelegramNick() + " " +
                "" + " в личном сообщении!");
    }

    private void getCheckReport(Update update) {

        Message message = update.callbackQuery().message();
        long chatId = message.chat().id();

        List<Report> reportList = reportService.getAllReport().stream().toList();

        for (Report report : reportList) {
            if (report.getStatusReport() == StatusReport.DEFAULT || report.getStatusReport() == StatusReport.NOT_ACCEPTED) {
                checkReport = report;
                break;
            }
            else if (report.getStatusReport() == StatusReport.ACCEPTED) {
                checkReport = null;
            }
        }
        if (checkReport == null) {
            sendMessage(chatId, "Отчетов нет!");
            throw new NotFoundReportException("Отчетов нет!");
        }
        User guest = checkReport.getUserId();
        String name = guest.getTelegramNick();

        SendMessage sendMessage =
                new SendMessage(chatId, "Отчет от " + '@' + name +
                        ", был отправлен " + checkReport.getDateReport() + " :\n" +
                        "отчет: " + checkReport.getReportText() + "\n");
        SendPhoto sendPhoto = new SendPhoto(chatId, checkReport.getPicture());
        telegramBot.execute(sendPhoto);

        sendMessage.replyMarkup(inlineKeyboardMarkupService.createButtonsCheckReport());

        sendMessage(sendMessage);
    }

    private void sendMessage(SendMessage sendMessage) {

        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (sendResponse != null && !sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private void sendMessage(long chatId, String message) {

        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private void sendMessage1(SendMessage message) {
        SendResponse sendResponse = telegramBot.execute(message);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private void sendMessage2(long chatId, String text, InlineKeyboardMarkup buttons) {
        SendMessage message = new SendMessage(chatId, text);
        message.replyMarkup(buttons);
        sendMessage1(message);
    }

    private void recordingNewAnimals(Update update) {

        Message message = update.message();
        String text = message.text();
        String userName = message.from().firstName();
        long chatId = message.chat().id();
        long telegramId;
        long petID;

        if (text != null) {
            Matcher matcher = patternAdopter.matcher(text);
            if (matcher.find()) {

                telegramId = Long.parseLong(matcher.group(1));
                petID = Long.parseLong(matcher.group(2));

                User userId = userService.findUserByTelegramId(telegramId);
                Pet petId = petRepository.getReferenceById(petID);

                Adopter adopter = new Adopter(userId, petId);
                Adopter adopterOne = adopterRepository.findAdopterByUserId(userId);

                if (adopterOne == null) {
                    adopter.getUser().setUserType(UserType.VOLUNTEER);
                    adopterRepository.save(adopter);
                    sendMessage(chatId, "усыновитель добавлен");
                    welcomeVolunteer(chatId, userName);
                } else {
                    sendMessage(chatId, "усыновитель уже есть в БД");
                }
            } else {
                sendMessage(chatId, "некорректно введены данные");
            }
        }
    }

    public void sendingTimer(long chatId) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), HOUR_OF_DAY, MINUTE, SECOND);
        if (now.compareTo(nextRun) > 0) {
            nextRun = nextRun.plusDays(1);
        }
        long initialDelay = Duration.between(now, nextRun).getSeconds();
        long period = PERIOD_SECONDS;
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Report reportLast = reportRepository.getReferenceById(chatId);
                if (reportLast.getDateReport().isBefore(LocalDate.now().minusDays(2))) {
                    sendMessage(chatId, "Вы находитесь на испытательном сроке с животным, " +
                            "пожалуйста, предоставьте отчёт за последние 2 дня.");
                }
            }
        }, initialDelay, period, TimeUnit.SECONDS);
    }
}

