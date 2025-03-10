package com.example.demo.controller;

import com.example.demo.service.JasperReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class JasperReportController {

    private final JasperReportService jasperReportService;

    public JasperReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    @PostMapping("/resume")
    public ResponseEntity<byte[]> generateResumeReport(@RequestBody Map<String, String> employeeData) throws JRException {
        String employeeId = employeeData.get("employeeId");
        String name = employeeData.get("name");
        String email = employeeData.get("email");
        String phone = employeeData.get("phone");
        String skills = employeeData.get("skills");

        byte[] pdfBytes = jasperReportService.generateResumePdf(employeeId, name, email, phone, skills);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "resume.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
    @PostMapping("/salary")
    public ResponseEntity<byte[]> generateSalaryReport(@RequestBody Map<String, Object> salarydata) throws Exception{
            String employeeId = (String) salarydata.get("employeeId");
            String name = (String) salarydata.get("name");
            String email = (String) salarydata.get("email");
            String phone = (String) salarydata.get("phone");
            double basicSalary = ((Number) salarydata.get("basicSalary")).doubleValue();
            double allowances = ((Number) salarydata.get("allowances")).doubleValue();
            double deductions = ((Number) salarydata.get("deductions")).doubleValue();
            double netSalary = ((Number) salarydata.get("netSalary")).doubleValue();
            
            byte[] pdfBytes = jasperReportService.generateSalaryPdf(employeeId, name, email, phone, basicSalary, allowances, deductions, netSalary);
            HttpHeaders headers= new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "salary_slip.pdf");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}