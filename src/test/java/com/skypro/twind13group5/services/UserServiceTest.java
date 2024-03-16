package com.skypro.twind13group5.services;

import com.skypro.twind13group5.enums.UserType;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.repository.UserRepository;
import com.skypro.twind13group5.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    void testAddUser() {
        User expectedUser = new User(12345L, "testUser", UserType.DEFAULT);
        when(userRepository.findByTelegramId(12345L)).thenReturn(null);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = userService.addUser(12345L, "testUser", UserType.DEFAULT);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testAddPhoneNumber() {
        User expectedUser = new User(12345L, "testUser", UserType.DEFAULT, "+79999999999");
        when(userRepository.findByTelegramId(12345L)).thenReturn(null);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = new User(12345L, "testUser", UserType.DEFAULT);
        userService.addPhoneNumber(12345L, "+79999999999");
        when(userRepository.findByTelegramId(12345L)).thenReturn(null);
        when(userRepository.save(actualUser)).thenReturn(actualUser);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testFindUserByTelegramId() {
        User expectedUser = new User(12345L, "testUser", UserType.DEFAULT);
        when(userRepository.findByTelegramId(12345L)).thenReturn(expectedUser);

        User actualUser = userService.findUserByTelegramId(12345L);

        assertEquals(expectedUser, actualUser);
    }
    @Test
    void testGetAllUsers() {
        Collection<User> expectedUsers = List.of(
                new User(1L, "user1", UserType.DEFAULT),
                new User(2L, "user2", UserType.VOLUNTEER)
        );
        when(userRepository.findAll()).thenReturn((List<User>) expectedUsers);

        Collection<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }
}
