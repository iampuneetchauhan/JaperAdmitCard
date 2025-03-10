package com.example.demo.service;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportService {

    public byte[] generateResumePdf(String employeeId, String name, String email, String phone, String skills) throws JRException {
        try {
            // Load the JRXML file for the employee resume
            InputStream reportStream = new ClassPathResource("reports/hello_world.jrxml").getInputStream();

            // Compile the JRXML file into a JasperReport
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Create a map to store employee data
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("employeeId", employeeId);
            parameters.put("name", name);
            parameters.put("email", email);
            parameters.put("phone", phone);
            parameters.put("skills", skills);

            // Fill the report with provided employee data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Export the report to a PDF byte array
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new JRException("Error generating resume PDF", e);
        }
    }

    public byte[] generateSalaryPdf(String employeeId, String name, String email, String phone, 
                                    double basicSalary, double allowances, double deductions, double netSalary) throws JRException {
        try {
            // Load the JRXML file for the salary slip
            InputStream reportStream = new ClassPathResource("reports/hello_world.jrxml").getInputStream();

            // Compile the JRXML file into a JasperReport
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Create a map to store salary details
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("employeeId", employeeId);
            parameters.put("name", name);
            parameters.put("email", email);
            parameters.put("phone", phone);
            parameters.put("basicSalary", basicSalary);
            parameters.put("allowances", allowances);
            parameters.put("deductions", deductions);
            parameters.put("netSalary", netSalary);

            // Fill the report with provided salary data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Export the report to a PDF byte array
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new JRException("Error generating salary PDF", e);
        }
    }
}
