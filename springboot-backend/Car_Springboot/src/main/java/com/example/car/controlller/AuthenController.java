package com.example.car.controlller;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.car.DTO.AuthenticationRequest;
import com.example.car.DTO.AuthenticationResponse;
import com.example.car.DTO.SingupRequest;
import com.example.car.DTO.UserDTO;
import com.example.car.Util.JWTUtil;
import com.example.car.entity.User;
import com.example.car.resposie.UserRepository;
import com.example.car.service.auth.authenService;
import com.example.car.service.jwt.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;  
import org.springframework.security.authentication.DisabledException;  
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;  


@RestController
@RequestMapping("api/authen")
@RequiredArgsConstructor
public class AuthenController {

	private final authenService authenService;
	private final AuthenticationManager authenticationManager ;
	private final UserService userService;
	private final JWTUtil jwtUtil;
	private final UserRepository userRepositony;
	

 


	public AuthenController(com.example.car.service.auth.authenService authenService,
			AuthenticationManager authenticationManager, UserService userService, JWTUtil jwtUtil,
			UserRepository userRepositony) {
		super();
		this.authenService = authenService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.userRepositony = userRepositony;
	}

	@PostMapping("/singup")
    public ResponseEntity<?> sigupCustomer(@RequestBody SingupRequest sigupreRequest) {
        if (authenService.hasCustomerWithEmail(sigupreRequest.getEmail())) {
            return new ResponseEntity<>("Khách hàng đã tồn tại với email", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO createCustomerDTO = authenService.createCustomer(sigupreRequest);
        if (createCustomerDTO == null) {
            return new ResponseEntity<>("Khách hàng chưa được tạo, hãy quay lại sau", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createCustomerDTO, HttpStatus.CREATED);
    }
	@PostMapping("/login") // Đánh dấu phương thức xử lý yêu cầu HTTP POST tại endpoint "/login"  
	public AuthenticationResponse loginRequest(@RequestBody AuthenticationRequest authenticationRequest)   
	        throws BadCredentialsException, DisabledException, UsernameNotFoundException {  

	    // Khởi tạo một biến để chứa thông tin phản hồi  
	    AuthenticationResponse authenticationResponse;  

	    try {  
	        // Xác thực thông tin đăng nhập qua authenticationManager  
	        // UsernamePasswordAuthenticationToken chứa username và password   
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(  
	                authenticationRequest.getEmail(), // Lấy email từ yêu cầu  
	                authenticationRequest.getPassword() // Lấy password từ yêu cầu  
	        ));  
	    } catch (BadCredentialsException e) {  
	        // Nếu thông tin đăng nhập không đúng, ném ra ngoại lệ với thông báo thích hợp  
	        throw new BadCredentialsException("Incorrect username or password.");  
	    }  

	    // Nếu xác thực thành công, lấy thông tin người dùng  
	    final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());  

	    // Tìm kiếm người dùng trong cơ sở dữ liệu dựa trên email  
	    Optional<User> optionalUser = userRepositony.findFirstByEmail(userDetails.getUsername());  

	    // Tạo JSON Web Token (JWT) cho người dùng  
	    final String jwt = jwtUtil.generateToken(userDetails); // Sử dụng jwtutil để tạo token  

	    // Khởi tạo đối tượng authenticationResponse để trả về  
	    authenticationResponse = new AuthenticationResponse(); // Tạo mới đối tượng phản hồi  

	    if (optionalUser.isPresent()) { // Kiểm tra nếu người dùng tồn tại  
	        // Nếu có người dùng, đặt thông tin JWT và ID của người dùng vào response  
	        authenticationResponse.setJwt(jwt); // Đặt JWT vào phản hồi  
	        authenticationResponse.setUserid(optionalUser.get().getId()); // Lấy ID người dùng từ optionalUser  
	        authenticationResponse.setUserRole(optionalUser.get().getUserRole()); // Lấy vai trò người dùng từ optionalUser  
	    }  

	    // Trả về phản hồi chứa JWT và thông tin người dùng  
	    return authenticationResponse;  
	}
}