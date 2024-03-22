package com.skypro.twind13group5.repository;

import com.skypro.twind13group5.enums.*;
import com.skypro.twind13group5.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Long> {


    @Modifying
    @Query("UPDATE Adopter a SET " +
            "a.user = :user_id, " +
            "a.pet = :pet_id" +
            " WHERE a.id = :id"
            )
    void updateAdopterById(
            @Param("id") Long id,
            @Param("user_id") User user,
            @Param("pet_id") Pet pet);

    @Query("SELECT a FROM Adopter a WHERE a.user = :user_id")
    Adopter findAdopterByUserId(@Param("user_id") User user);
}
