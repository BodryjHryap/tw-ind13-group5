package com.skypro.twind13group5.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.skypro.twind13group5.service.UserRequestsService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Объект, уведомляемый о событии.
 * Он должен быть зарегистрирован источником событий
 * и реализовывать методы для получения и обработки уведомлений.
 */

@Component
public class TelegramBotUpdateListener implements UpdatesListener {

    private final UserRequestsService userRequestsService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);
    private final TelegramBot telegramBot;

    public TelegramBotUpdateListener(UserRequestsService userRequestsService, TelegramBot telegramBot) {
        this.userRequestsService = userRequestsService;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     *Позволяет пользователю выбирать необходимый блок с информацией, в том числе о конкретном животном.
     *Передает пользователю информацию о приюте и животных.
     *В случае, если ни один из вариантов не подошел, то можно вызвать волонтера.
     *Если обращение не первое, то пользователь начнет диалог с выбора приюта.
     *Не пытайтесь менять сообщение, отправленное боту. Это приведет к падению приложения и проблемами с ним в дальнейшем.
     */
    @Override
    public int process(List<Update> updates) {
        try {

            updates.forEach(update -> {
                logger.info("Handles update: {}", update);

                if (userRequestsService.checkReport(update)) {
                    return;
                }
                if (userRequestsService.checkVolunteer(update)) {
                    return;
                }
                if (userRequestsService.checkContactDetails(update)) {
                    return;
                }
                if (userRequestsService.checkAdopter(update)) {
                    return;
                }
                if (update.message() == null) {
                    userRequestsService.createButton(update);
                } else {
                    userRequestsService.sendMessageStart(update);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
