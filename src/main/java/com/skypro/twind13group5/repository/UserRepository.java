package com.skypro.twind13group5.repository;

import com.skypro.twind13group5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.telegramId = :telegram_id")
    User findByTelegramId(@Param("telegram_id")long telegramId);

    @Query("SELECT u FROM User u")
    List<User> getAllUsers();
}
