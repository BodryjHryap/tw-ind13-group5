package com.skypro.twind13group5.service;

import com.skypro.twind13group5.enums.StatusReport;
import com.skypro.twind13group5.model.Report;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.repository.ReportRepository;
import com.skypro.twind13group5.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public Report saveReport(User userId, String text, byte[] photo, LocalDate dateReport, StatusReport statusReport) {
        Report report = new Report(userId, text, photo, dateReport, statusReport);
        return reportRepository.save(report);
    }

    public void updateReportByUserId(User userId, String text, byte[] photo, LocalDate dateReport, StatusReport statusReport) {
    }
}
