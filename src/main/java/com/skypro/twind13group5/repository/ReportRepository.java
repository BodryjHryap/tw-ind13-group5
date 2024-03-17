package com.skypro.twind13group5.repository;

import com.skypro.twind13group5.model.Report;
import com.skypro.twind13group5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.userId = :user_id")
    Report findReportByUserId(@Param("user_id") User user);
}
