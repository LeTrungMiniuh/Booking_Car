package com.example.car.DTO;

import lombok.Data;

@Data
public class SearchCarDto {
	
	private String brand ;
	private String type;
	private String transmission;
	private String color;
	
	
	
	
	public SearchCarDto() {
		super();
	}




	public SearchCarDto(String brand, String type, String transmission, String color) {
		super();
		this.brand = brand;
		this.type = type;
		this.transmission = transmission;
		this.color = color;
	}




	public String getBrand() {
		return brand;
	}




	public void setBrand(String brand) {
		this.brand = brand;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public String getTransmission() {
		return transmission;
	}




	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}




	public String getColor() {
		return color;
	}




	public void setColor(String color) {
		this.color = color;
	}
	
	
	
	
	

}
