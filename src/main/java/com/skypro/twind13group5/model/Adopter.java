package com.skypro.twind13group5.model;

import com.skypro.twind13group5.enums.Shelter;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "adopters")
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopters")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(name = "shelter")
    private Shelter shelter;
}
