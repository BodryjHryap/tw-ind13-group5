package com.skypro.twind13group5.services;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.skypro.twind13group5.enums.UserStatus;
import com.skypro.twind13group5.enums.UserType;
import com.skypro.twind13group5.repository.UserRepository;
import com.skypro.twind13group5.service.UserRequestsService;
import com.skypro.twind13group5.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRequestsServiceTest {

    private static final String CORRECT_USER_NAME = "Nick";
    private static final long CORRECT_USER_ID = 123456789;
    private static final UserType CORRECT_USER_TYPE = UserType.DEFAULT;
    private static final UserStatus CORRECT_USER_STATUS = UserStatus.APPROVE;

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRequestsService userRequestsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSendMessageStart() {
        com.skypro.twind13group5.model.User user = null;
        when(userService.findUserByTelegramId(1L)).thenReturn(user);
    }

    @Test
    void testWelcomeNewUser() {
        com.skypro.twind13group5.model.User user = new com.skypro.twind13group5.model.User(CORRECT_USER_ID, CORRECT_USER_NAME, CORRECT_USER_TYPE, CORRECT_USER_STATUS);
        when(userService.findUserByTelegramId(1L)).thenReturn(user);
    }

    @Test
    void testWelcomeNotNewUser() {
        com.skypro.twind13group5.model.User user = new com.skypro.twind13group5.model.User(CORRECT_USER_ID, CORRECT_USER_NAME, CORRECT_USER_TYPE, CORRECT_USER_STATUS);
        when(userService.findUserByTelegramId(1L)).thenReturn(user);
    }

    @Test
    void testCreateButton() {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1234L);
        when(callbackQuery.data()).thenReturn("CLICK_CAT_SHELTER");

        userRequestsService.createButton(update);
    }
}
