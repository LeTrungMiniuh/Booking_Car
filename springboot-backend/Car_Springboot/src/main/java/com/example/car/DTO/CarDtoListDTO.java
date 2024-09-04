package com.example.car.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CarDtoListDTO {
	
	private List<CarDTO> carDtolist;

	public List<CarDTO> getCarDtolist() {
		return carDtolist;
	}

	public void setCarDtolist(List<CarDTO> carDtolist) {
		this.carDtolist = carDtolist;
	}
	

}
