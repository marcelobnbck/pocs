package com.company.sales;

public class App {
    public static void main(String[] args) {
        String topology = System.getenv().getOrDefault("TOPOLOGY", "city");
        if ("country".equalsIgnoreCase(topology)) {
            TopSalesmanCountryApp.run();
        } else {
            TopSalesPerCityApp.run();
        }
    }
}
