package org.example.cars;

import org.example.cars.model.Car;
import org.example.cars.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarServiceTest {

    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService();
    }

    @Test
    @Order(1)
    void shouldAddCarSuccessfully() {
        var car = new Car("Tesla Model 3", 2023);
        carService.addCar(car);

        assertThat(carService.getCars()).hasSize(1)
                .extracting(Car::getModel)
                .contains("Tesla Model 3");
    }

    @Test
    @Order(2)
    void shouldThrowWhenAddingDuplicateCar() {
        var car = new Car("BMW i8", 2020);
        carService.addCar(car);

        assertThatThrownBy(() -> carService.addCar(car))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Car already exists");
    }

    @Test
    void shouldCalculateTotalMileage() {
        var car1 = new Car("Honda Civic", 2022);
        var car2 = new Car("Ford Mustang", 2021);

        car1.drive(100);
        car2.drive(250);

        carService.addCar(car1);
        carService.addCar(car2);

        assertThat(carService.getTotalMileage()).isEqualTo(350.0);
    }
}
