package com.skypro.twind13group5.service;

import com.skypro.twind13group5.enums.ShelterType;
import com.skypro.twind13group5.enums.UserStatus;
import com.skypro.twind13group5.exception.NotFoundUserException;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.repository.UserRepository;
import com.skypro.twind13group5.enums.UserType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.Collection;
import java.util.List;

/**
 * Сервис и бизнес-логика по работе с пользователями телеграм бота.
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    private User user;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(long telegramId,
                        String nickName,
                        UserType userType,
                        UserStatus userStatus) {

        User newUser = new User(telegramId, nickName, userType, userStatus);
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            userRepository.save(newUser);
            return newUser;
        }
        return user;
    }

    @Transactional
    public User addGuest(long telegramId,
                         String nickName,
                         UserType userType,
                         ShelterType shelterType,
                         UserStatus userStatus,
                         String firstName,
                         String lastName,
                         String phoneNumber,
                         String carNumber) {

        User newGuest = new User(telegramId,
                nickName,
                firstName,
                lastName,
                phoneNumber,
                shelterType,
                carNumber,
                userType,
                userStatus);
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            throw new NotFoundUserException("Пользователь не найден!");
        }
        userRepository.updateUserInGuestById(
                firstName,
                lastName,
                phoneNumber,
                carNumber,
                shelterType,
                userType,
                userStatus,
                telegramId);

        return newGuest;
    }

    @Transactional
    public User addAdopterOrVolunteer(long telegramId,
                                      String nickName,
                                      UserType userType,
                                      ShelterType shelterType,
                                      UserStatus userStatus,
                                      String firstName,
                                      String lastName,
                                      String phoneNumber,
                                      String carNumber,
                                      String email,
                                      String address) {

        User newAdopterOrVolunteer = new User(telegramId,
                nickName,
                firstName,
                lastName,
                phoneNumber,
                carNumber,
                email,
                address,
                shelterType,
                userType,
                userStatus);
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            throw new NotFoundUserException("Пользователь не найден!");
        }
        userRepository.updateGuestInAdopterById(telegramId,
                firstName,
                lastName,
                phoneNumber,
                carNumber,
                userType,
                userStatus,
                email,
                address);

        return newAdopterOrVolunteer;
    }

@Transactional
    public void addPhoneNumber(long telegramId, String phoneNumber) {
        userRepository.updateUserPhone(telegramId,phoneNumber);
    }

    public User findUserByTelegramId(long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Collection<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateStatusUserById(Long id, UserStatus userStatus) {

        userRepository.updateStatusUserById(id, userStatus);
    }
}
