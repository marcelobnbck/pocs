package org.example.elasticsearch;

import org.example.elasticsearch.model.Car;
import org.example.elasticsearch.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarRepositoryTest {

    @Autowired
    private CarRepository repo;

    @Test
    void testFindByBrand() {
        repo.save(new Car("1", "Ford", "Mustang", 2023));
        List<Car> cars = repo.findByBrand("Ford");
        assertThat(cars).isNotEmpty();
        assertThat(cars.get(0).getModel()).isEqualTo("Mustang");
    }

}
