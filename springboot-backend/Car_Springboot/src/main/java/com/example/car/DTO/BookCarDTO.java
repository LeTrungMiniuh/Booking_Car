package com.example.car.DTO;

import java.sql.Date;

import com.example.car.enums.BookCarStatus;

import lombok.Data;

@Data
public class BookCarDTO  {
	
	
	private Long id;  

	private Date fromDate;  
	
	private Date toDate;  
	
	private Long days;  
	
	private Long price;  
	
	private BookCarStatus bookCarStatus;  

   private Long carid;

    private Long userId ;
    
    private String username;
    
    private String email;
    
    

	public BookCarDTO() {
		super();
	}







	public BookCarDTO(Long id, Date fromDate, Date toDate, Long days, Long price, BookCarStatus bookCarStatus,
			Long cardId, Long userid, String username, String email) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.days = days;
		this.price = price;
		this.bookCarStatus = bookCarStatus;
		this.carid = cardId;
		this.userId = userid;
		this.username = username;
		this.email = email;
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







	public Long getCardId() {
		return carid;
	}







	public void setCardId(Long cardId) {
		this.carid = cardId;
	}







	public Long getUserid() {
		return userId;
	}







	public void setUserid(Long userid) {
		this.userId = userid;
	}







	public String getUsername() {
		return username;
	}







	public void setUsername(String username) {
		this.username = username;
	}







	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}





   
}
