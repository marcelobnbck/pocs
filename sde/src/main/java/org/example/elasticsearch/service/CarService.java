package org.example.elasticsearch.service;

import org.example.elasticsearch.model.Car;
import org.example.elasticsearch.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository repo;

    public void demo() {
        repo.save(new Car("1", "Tesla", "Model S", 2025));
        repo.findByBrand("Tesla").forEach(System.out::println);
    }

}
