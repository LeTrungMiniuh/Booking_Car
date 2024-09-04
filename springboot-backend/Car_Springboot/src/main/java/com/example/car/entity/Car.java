package com.example.car.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import com.example.car.DTO.CarDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "cars")
public class Car implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType. IDENTITY)
	private Long id;
	private String brand;
	private String 	color;
	private String name;
	private String type;
	private String transmission;
	private String description;
	private Long price;
	private Date year;
	@Column(columnDefinition = "longblob")
	private byte[] image;
	
	
	public CarDTO getCarDTO() {
		
		CarDTO carDTO = new CarDTO();
		carDTO.setId(id);
		carDTO.setName(name);
		carDTO.setBrand(brand);
		carDTO.setColor(color);
		carDTO.setPrice(price);
		carDTO.setDescription(description);
		carDTO.setType(type);
		carDTO.setTransmission(transmission);
		carDTO.setYear(year);
		carDTO.setRetunrnedImage(image);
		
		return carDTO;
	   
	}
	
	
	public Car() {
		super();
	}
    

	public Car(Long id, String brand, String color, String name, String type, String transmission, String description,
			Long price, Date year, byte[] image) {
		super();
		this.id = id;
		this.brand = brand;
		this.color = color;
		this.name = name;
		this.type = type;
		this.transmission = transmission;
		this.description = description;
		this.price = price;
		this.year = year;
		this.image = image;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Long getPrice() {
		return price;
	}


	public void setPrice(Long price) {
		this.price = price;
	}


	public Date getYear() {
		return year;
	}


	public void setYear(Date year) {
		this.year = year;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	
	
	
	

}
