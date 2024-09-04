package com.example.car.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.car.entity.User;
import com.example.car.resposie.UserRepository;

import lombok.RequiredArgsConstructor;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RequiredArgsConstructor
public  class UserServiceImpl implements  UserService {


	    private final UserRepository userRepository;


	   
	    public UserServiceImpl(UserRepository userRepository) {
			super();
			this.userRepository = userRepository;
		}


	    @Override  
	    public UserDetailsService userDetailsService() {  
	        return new UserDetailsService() {  
	            
	            @Override  
	            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
	                try {  
	                    return userRepository.findFirstByEmail(username)  
	                            .orElseThrow(() -> new UserPrincipalNotFoundException("User not found"));  
	                } catch (UserPrincipalNotFoundException e) {  
	                    throw new UsernameNotFoundException(e.getMessage(), e);  
	                }  
	            }  
	        };  
	    }

	  

		

}
