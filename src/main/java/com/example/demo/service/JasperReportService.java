package com.example.demo.service;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportService {

    public byte[] generateAdmitCardPdf(String rollNo, String name, String fatherName, String course,
                                       String examDate, String examTime, String examCentre) throws JRException {
        try {
            // Load the JRXML file
            InputStream reportStream = new ClassPathResource("reports/hello_world.jrxml").getInputStream();

            // Compile the JRXML file
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Load images as InputStream
            InputStream logo1Stream = new ClassPathResource("static/LOGO1.jpg").getInputStream();
            InputStream logo2Stream = new ClassPathResource("static/LOGO2.jpg").getInputStream();
            InputStream logo3Stream = new ClassPathResource("static/LOGO3.jpg").getInputStream();
            InputStream qrCodeStream = new ClassPathResource("static/qr.jpg").getInputStream();

            // Prepare parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("RollNo", rollNo);
            parameters.put("Name", name);
            parameters.put("FatherName", fatherName);
            parameters.put("Course", course);
            parameters.put("ExamDate", examDate);
            parameters.put("ExamTime", examTime);
            parameters.put("ExamCentre", examCentre);

            // Pass images as InputStream
            parameters.put("LOGO1", logo1Stream);
            parameters.put("LOGO2", logo2Stream);
            parameters.put("LOGO3", logo3Stream);
            parameters.put("QR_CODE", qrCodeStream);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Export the report to a PDF byte array
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new JRException("Error generating admit card PDF", e);
        }
    }
}
