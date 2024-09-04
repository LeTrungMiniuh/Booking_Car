 	package com.example.car.resposie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.car.DTO.BookCarDTO;
import com.example.car.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	

}
