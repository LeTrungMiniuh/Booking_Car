package com.example.car.resposie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.car.entity.BookCar;
@Repository
public interface Bookcarresposie  extends JpaRepository<BookCar, Long>{
	
	List<BookCar> findAllByUserId(Long userId);

}
