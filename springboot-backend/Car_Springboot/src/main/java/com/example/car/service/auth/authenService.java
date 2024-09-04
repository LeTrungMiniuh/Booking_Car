package com.example.car.service.auth;

import com.example.car.DTO.SingupRequest;
import com.example.car.DTO.UserDTO;

public interface authenService {
	
  UserDTO createCustomer (SingupRequest singupRequest);
	
	boolean hasCustomerWithEmail(String Email);

}
