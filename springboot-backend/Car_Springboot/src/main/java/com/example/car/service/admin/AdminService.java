package com.example.car.service.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.car.DTO.BookCarDTO;
import com.example.car.DTO.CarDTO;
import com.example.car.DTO.CarDtoListDTO;
import com.example.car.DTO.SearchCarDto;
import com.example.car.entity.Car;

public interface AdminService {

	
	boolean postCar (CarDTO carDTO) throws IOException;
	
	List<CarDTO> getAllCars();
	
	void deleteCar(Long id);
	
	// kiểm tra lấy thông tin 1 sản phẩm 
	CarDTO getcarById(Long id);
	
	// cập nhập thông tin Car
	boolean updateCar (Long  carid, CarDTO carDTO ) throws IOException;
	
	// lấy danh sachs tất cả thông tin đặt xe 
	List<BookCarDTO> getBooking();
	
	// kiêmt tra trạn thái khi đặt xe 
	
	boolean changeBookingStatus(Long bookingId , String status);
	
	// tìm car
	CarDtoListDTO searchCar(SearchCarDto searchCarDto);
}