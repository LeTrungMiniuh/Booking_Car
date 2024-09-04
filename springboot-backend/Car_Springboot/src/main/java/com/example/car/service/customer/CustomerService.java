package com.example.car.service.customer;

import java.util.List;

import com.example.car.DTO.BookCarDTO;
import com.example.car.DTO.CarDTO;
import com.example.car.DTO.CarDtoListDTO;
import com.example.car.DTO.SearchCarDto;

public interface CustomerService {
	 // lây cả đối tượng 
	List<CarDTO> getAllCars ();
    boolean  bookACar(BookCarDTO bookCarDTO);
    CarDTO getallIdCar(Long Carid);
    List<BookCarDTO> getBookingByUserid(Long userId);
    
    CarDtoListDTO searchCar (SearchCarDto searchCarDto);
}
