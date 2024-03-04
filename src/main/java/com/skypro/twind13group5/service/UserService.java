package com.skypro.twind13group5.service;

import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.repository.UserRepository;
import com.skypro.twind13group5.enums.UserType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(long telegramId, String nickName, UserType userType) {

        User newUser = new User(telegramId, nickName, userType);
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            userRepository.save(newUser);
            return newUser;
        }
        return user;
    }

    @Transactional
    public void addPhoneNumber(long telegramId) {
        User user = userRepository.findByTelegramId(telegramId);
        userRepository.updateUserPhone();
    }

    @Transactional
    public User findUserByTelegramId(long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
