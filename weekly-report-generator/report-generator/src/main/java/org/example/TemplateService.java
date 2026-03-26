package org.example;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TemplateService {
    private final Handlebars handlebars;

    public TemplateService() {
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        this.handlebars = new Handlebars(loader);
    }

    public String createFinalReport(Map<String, Object> data) throws IOException {
        Template template = handlebars.compile("weekly-report");
        return template.apply(data);
    }

    public void saveFile(String content) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm"));
        String fileName = "1:1-Weekly-Report_" + timestamp + ".txt";

        Path path = Paths.get("reports");
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        try (FileWriter writer = new FileWriter(path.resolve(fileName).toFile())) {
            writer.write(content);
        }
        System.out.println("Report saved at: " + path.resolve(fileName));
    }
}
