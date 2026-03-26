package org.example;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Map;

public class ReportService {
    private final TemplateService templateService;
    private final InputCollector inputCollector;

    public ReportService() {
        this.templateService = new TemplateService();
        this.inputCollector = new InputCollector();
    }

    public void generateReport() {
        try {
            Map<String, Object> reportData = inputCollector.collectReportData();
            reportData.put("generatedDate", ZonedDateTime.now().toString());
            String reportContent = templateService.createFinalReport(reportData);
            templateService.saveFile(reportContent);
            System.out.println("\n--- Report generated successfully ---");
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        } finally {
            inputCollector.close();
        }
    }
}
