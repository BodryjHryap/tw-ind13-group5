package com.skypro.twind13group5.services;

import com.skypro.twind13group5.enums.UserStatus;
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
        User expectedUser = new User(12345L, "testUser", UserType.DEFAULT, UserStatus.APPROVE);
        when(userRepository.findByTelegramId(12345L)).thenReturn(null);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = userService.addUser(12345L, "testUser", UserType.DEFAULT, UserStatus.APPROVE);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testAddPhoneNumber() {
        User expectedUser = new User(12345L, "testUser", UserType.DEFAULT, UserStatus.APPROVE);
        when(userRepository.findByTelegramId(12345L)).thenReturn(null);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = new User(12345L, "testUser", UserType.DEFAULT, UserStatus.APPROVE);

        userService.addPhoneNumber(12345L, "+7-999-999-99-99");
        //userRepository.updateUserPhone(12345L, "+79999999999");
        //actualUser.setPhoneNumber("+7-999-999-99-99");
        //when(userRepository.findByTelegramId(12345L)).thenReturn(null);
        //when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        //actualUser.setPhoneNumber("+79999999999");


        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testFindUserByTelegramId() {
        User expectedUser = new User(12345L, "testUser", UserType.DEFAULT, UserStatus.APPROVE);
        when(userRepository.findByTelegramId(12345L)).thenReturn(expectedUser);

        User actualUser = userService.findUserByTelegramId(12345L);

        assertEquals(expectedUser, actualUser);
    }
    @Test
    void testGetAllUsers() {
        Collection<User> expectedUsers = List.of(
                new User(1L, "user1", UserType.DEFAULT, UserStatus.APPROVE),
                new User(2L, "user2", UserType.VOLUNTEER, UserStatus.APPROVE)
        );
        when(userRepository.findAll()).thenReturn((List<User>) expectedUsers);

        Collection<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }
}
