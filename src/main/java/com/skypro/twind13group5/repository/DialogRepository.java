package com.skypro.twind13group5.repository;

import com.skypro.twind13group5.model.Dialog;
import com.skypro.twind13group5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query("SELECT d FROM Dialog d WHERE d.guestId = :guest_id")
    Dialog findDialogByUserId(@Param("guest_id") User guestId);
}
