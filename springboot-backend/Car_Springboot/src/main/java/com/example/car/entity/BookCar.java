package com.example.car.entity;

import java.io.Serializable;
import java.sql.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.car.DTO.BookCarDTO;
import com.example.car.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data

public class BookCar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long id;  
	private Date fromDate;  
	private Date toDate;  
	private Long days;  
	private Long price;  
	
	private BookCarStatus bookCarStatus;  
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "car_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) 
	@JsonIgnore
	private Car car;
	
	
	 // thông tin người đặt xe 
	
	public BookCarDTO  getBookACarDto( ) {
		
		     BookCarDTO bookACarDto  = new BookCarDTO();
		    bookACarDto.setId(id);;
		    bookACarDto.setDays(days);
		    bookACarDto.setBookCarStatus(bookCarStatus);
		    bookACarDto.setPrice(price);
		    bookACarDto.setToDate (toDate);
		    bookACarDto.setFromDate(fromDate);
		    bookACarDto.setCardId(car.getId());
		    bookACarDto.setUserid(user.getId());
		    bookACarDto.setUsername(user.getUsername());
		    bookACarDto.setEmail(user.getEmail());
		    return bookACarDto;
	}
	

	
	public BookCar() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Date getFromDate() {
		return fromDate;
	}



	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}



	public Date getToDate() {
		return toDate;
	}



	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}



	public Long getDays() {
		return days;
	}



	public void setDays(Long days) {
		this.days = days;
	}



	public Long getPrice() {
		return price;
	}



	public void setPrice(Long price) {
		this.price = price;
	}



	public BookCarStatus getBookCarStatus() {
		return bookCarStatus;
	}



	public void setBookCarStatus(BookCarStatus bookCarStatus) {
		this.bookCarStatus = bookCarStatus;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Car getCar() {
		return car;
	}



	public void setCar(Car car) {
		this.car = car;
	}



   
	
	
   
}
