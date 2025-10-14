package org.example.elasticsearch.repository;

import org.example.elasticsearch.model.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface CarRepository extends ElasticsearchRepository<Car, String> {
    List<Car> findByBrand(String brand);
}
