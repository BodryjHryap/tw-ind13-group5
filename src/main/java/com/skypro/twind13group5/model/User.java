package com.skypro.twind13group5.model;

import com.skypro.twind13group5.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "telegram_id")
    private long telegramId;

    @Column(name = "telegram_nick")
    private String telegramNick;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_type")
    private UserType userType;
    @Column(name = "phone_number")
    private String phone;

    public User(long telegramId, String telegramNick, UserType userType) {
        this.telegramId = telegramId;
        this.telegramNick = telegramNick;
        this.userType = userType;
    }

    public User(long telegramId, String telegramNick, UserType userType, String phone) {
        this.telegramId = telegramId;
        this.telegramNick = telegramNick;
        this.userType = userType;
        this.phone = phone;
    }

    public User() {
    }
}
