	package com.example.car.service.auth;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.stereotype.Service;
	
	import com.example.car.DTO.SingupRequest;
	import com.example.car.DTO.UserDTO;
	import com.example.car.entity.User;
	import com.example.car.enums.UserRole;
	import com.example.car.resposie.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
	@Service
	@RequiredArgsConstructor
	public class authenimple implements authenService {
		@Autowired
	    private final  UserRepository userRepository;
		
	

		public authenimple(UserRepository userRepository) {
			super();
			this.userRepository = userRepository;
		}
       
		
		@PostConstruct
	    public void createAdminAccount() {  
	        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);  
	        if (adminAccount == null) {  
	            User newAdminAccount = new User();  
	            newAdminAccount.setName("Admin");  
	            newAdminAccount.setEmail("admin@test.com");  
	            newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));  
	            newAdminAccount.setUserRole(UserRole.ADMIN);  
	            userRepository.save(newAdminAccount);  
	            System.out.println("Admin account created successfully");  
	        }  
	    }  

		@Override
		public UserDTO createCustomer(SingupRequest signupRequest) {
		    // Create a new User instance
		    User user = new User();
		    
		    // Set the user details from the signupRequest
		    user.setName(signupRequest.getName());
		    user.setEmail(signupRequest.getEmail());
		    // Password encoding using BCryptPasswordEncoder
		   user.setPassword( new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		   // Set the user role
		    user.setUserRole(UserRole.CUSTOMER);
		    // Save the user in the repository
		    User createdUser = userRepository.save(user);

            UserDTO userDTO = new UserDTO();
		   userDTO.setId(createdUser.getId());


		    return userDTO;
		}

	
	    @Override
	    public boolean hasCustomerWithEmail(String email) {
	        return userRepository.findFirstByEmail(email).isPresent();
	    }
	
	}
