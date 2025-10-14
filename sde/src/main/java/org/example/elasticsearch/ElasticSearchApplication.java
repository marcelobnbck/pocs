package org.example.elasticsearch;

import org.example.elasticsearch.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticSearchApplication implements CommandLineRunner {

    @Autowired
    private CarService service;

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }

    @Override
    public void run(String... args) {
        service.demo();
    }

}
