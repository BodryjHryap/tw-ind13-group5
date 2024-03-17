package com.skypro.twind13group5.service;

import com.skypro.twind13group5.enums.StatusReport;
import com.skypro.twind13group5.model.Report;
import com.skypro.twind13group5.model.User;
import com.skypro.twind13group5.repository.ReportRepository;
import com.skypro.twind13group5.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Сервис и бизнес-логика по работе с отчетами.
 */
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public Report saveReport(User userId,
                             LocalDate dateReport,
                             StatusReport statusReport,
                             String reportText,
                             byte[] picture) {

        Report report = new Report(userId,
                dateReport,
                statusReport,
                reportText,
                picture);

        return reportRepository.save(report);
    }

    @Transactional
    public void updateReportByUserId(User userId,
                                     LocalDate dateReport,
                                     StatusReport statusReport,
                                     String reportText,
                                     byte[] picture) {


        reportRepository.updateReportById(userId,
                dateReport,
                statusReport,
                reportText,
                picture);
    }

    @Transactional
    public void updateDateEndOfProbationById(User userId,
                                             LocalDate dateEndOfProbation) {

        reportRepository.updateDateEndOfProbationById(userId,
                dateEndOfProbation);
    }

    @Transactional
    public void updateStatusReportById(User userId,
                                       StatusReport statusReport) {

        reportRepository.updateStatusReportById(userId,
                statusReport);
    }

    public void deleteReportById(Long id) {

        reportRepository.deleteById(id);
    }

    public Collection<Report> getAllReport() {
        return reportRepository.findAll();
    }
}
