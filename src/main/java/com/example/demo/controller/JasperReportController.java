package com.example.demo.controller;

import com.example.demo.service.JasperReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class JasperReportController {

    private final JasperReportService jasperReportService;

    public JasperReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    @PostMapping("/admit-card")
    public ResponseEntity<byte[]> generateAdmitCard(@RequestBody Map<String, String> admitCardData) throws JRException {
        String rollNo = admitCardData.get("rollNo");
        String name = admitCardData.get("name");
        String fatherName = admitCardData.get("fatherName");
        String course = admitCardData.get("course");
        String examDate = admitCardData.get("examDate");
        String examTime = admitCardData.get("examTime");
        String examCentre = admitCardData.get("examCentre");

        byte[] pdfBytes = jasperReportService.generateAdmitCardPdf(rollNo, name, fatherName, course, examDate, examTime, examCentre);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "admit_card.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
