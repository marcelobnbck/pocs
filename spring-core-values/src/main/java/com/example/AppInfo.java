package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppInfo {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    public void displayInfo() {
        System.out.println("Application Name: " + appName);
        System.out.println("Application Version: " + appVersion);
    }
}

