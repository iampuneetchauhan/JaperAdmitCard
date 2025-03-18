package com.example.demo.controller;
import com.example.demo.service.JasperReportService;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class JasperReportController {

    private final JasperReportService admitCardService;

    public JasperReportController(JasperReportService admitCardService) {
        this.admitCardService = admitCardService;
    }

    @PostMapping("/admit-card")
    public ResponseEntity<?> generateAdmitCard(@RequestBody Map<String, String> admitCardData) {
        try {
            // Extract values safely
            String rollNo = admitCardData.getOrDefault("rollNo", "N/A");
            String name = admitCardData.getOrDefault("name", "N/A");
            String fatherName = admitCardData.getOrDefault("fatherName", "N/A");
            String course = admitCardData.getOrDefault("course", "N/A");
            String examDate = admitCardData.getOrDefault("examDate", "N/A");
            String examTime = admitCardData.getOrDefault("examTime", "N/A");
            String examCentre = admitCardData.getOrDefault("examCentre", "N/A");

            // Generate PDF
            byte[] pdfBytes = admitCardService.generateAdmitCardPdf(
                    rollNo, name, fatherName, course, examDate, examTime, examCentre);

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "admit_card.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating admit card: " + e.getMessage());
        }
    }
}

