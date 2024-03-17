package com.skypro.twind13group5.model;

import com.skypro.twind13group5.enums.StatusReport;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Класс животных, в котором передает Id, имя животного, тип животного
 * цвет, пол и породу
 */
@Data
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "report_text")
    private String reportText;

    @Column(name = "photo")
    private byte[] petPhoto;

    @Column(name = "date_report")
    private LocalDate dateReport;

    @Column(name = "status")
    private StatusReport status;

    public Report(User userId, String reportText, byte[] petPhoto, LocalDate dateReport, StatusReport status) {
        this.userId = userId;
        this.reportText = reportText;
        this.petPhoto = petPhoto;
        this.dateReport = dateReport;
        this.status = status;
    }
}
