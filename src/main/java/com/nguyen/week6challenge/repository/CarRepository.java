package com.nguyen.week6challenge.repository;

import com.nguyen.week6challenge.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
