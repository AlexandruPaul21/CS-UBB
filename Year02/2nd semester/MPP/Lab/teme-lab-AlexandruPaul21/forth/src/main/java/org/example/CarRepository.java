package org.example;

import java.util.List;

public interface CarRepository extends Repository<Integer, Car> {
    List<Car> findByManufacturer(String manufacturer);
    List<Car> findBetweenYears(int min, int max);
}
