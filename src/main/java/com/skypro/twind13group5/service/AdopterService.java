package com.skypro.twind13group5.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.skypro.twind13group5.enums.UserType;
import com.skypro.twind13group5.exception.NotFoundAdopterException;
import com.skypro.twind13group5.model.Adopter;
import com.skypro.twind13group5.model.Pet;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.repository.AdopterRepository;
import com.skypro.twind13group5.repository.PetRepository;
import com.skypro.twind13group5.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * Сервис и бизнес-логика по работе с усыновителями.
 */

@Service
public class AdopterService {

    private final AdopterRepository adopterRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public AdopterService(AdopterRepository adopterRepository, UserRepository userRepository, PetRepository petRepository) {
        this.adopterRepository = adopterRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    public Adopter saveAdopter(Adopter adopter) {
        return adopterRepository.save(adopter);
    }

    public Adopter saveAdopter2(User user, Pet pet) {
        Adopter adopter = new Adopter(user, pet);
        return adopterRepository.save(adopter);
    }

    @Transactional
    public void updateAdopterById(Long id,
                                  User user,
                                  Pet pet) {

        Adopter adopter = adopterRepository.getReferenceById(id);
        if (adopter == null) {
            throw new NotFoundAdopterException("Усыновитель не найден!");
        }
        adopterRepository.updateAdopterById(id, user, pet);
    }

    public void deleteAdopterById(Long id) {

        Adopter adopter = adopterRepository.getReferenceById(id);
        if (adopter == null) {
            throw new NotFoundAdopterException("Усыновитель не найден!");
        }
        adopterRepository.delete(adopter);
    }

    public Collection<Adopter> getAllAdopter() {
        return adopterRepository.findAll();
    }

    public Adopter foundAdopterById(long id) {
        Adopter adopter = adopterRepository.getReferenceById(id);
        if (adopter == null) {
            throw new NotFoundAdopterException(toString());
        }
        return adopter;
    }

    public Adopter recordingNewAdopter(long userId, long petId) {

        User user = userRepository.findById(userId).get();
        Pet pet = petRepository.findById(petId).get();
        user.setUserType(UserType.ADOPTER);

        Adopter adopter = new Adopter(user, pet);
        Adopter adopterOne = adopterRepository.findAdopterByUserId(user);

        if (adopterOne != null) {
            throw new NotFoundAdopterException("Усыновитель уже добавлен!");
        }

        return adopterRepository.save(adopter);
    }

}
