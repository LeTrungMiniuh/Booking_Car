package com.example.car.controlller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.car.DTO.BookCarDTO;
import com.example.car.DTO.CarDTO;
import com.example.car.DTO.CarDtoListDTO;
import com.example.car.DTO.SearchCarDto;
import com.example.car.service.customer.CustomerServiceImpl;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
   
	private final CustomerServiceImpl customerServiceImpl;

	public CustomerController(CustomerServiceImpl customerServiceImpl) {
		super();
		this.customerServiceImpl = customerServiceImpl;
	}
	
	
	@GetMapping("/cars")  
	public ResponseEntity<List<CarDTO>> getAllCars() {  
	    List<CarDTO> carDtoList = customerServiceImpl.getAllCars();  
	    return ResponseEntity.ok(carDtoList);  
	}
	
	@PostMapping("/car/book/{carId}")  
	public ResponseEntity<Void> bookACar(@RequestBody BookCarDTO bookACarDto) {  
	    boolean success = customerServiceImpl.bookACar(bookACarDto);  
	    if (success) {  
	        return ResponseEntity.status(HttpStatus.CREATED).build();  
	    }  
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  
	}  
	

	
	@GetMapping("/car/{carId}")  
	public ResponseEntity<CarDTO> getCarById(@PathVariable Long carId) {  
		CarDTO carDto = customerServiceImpl.getallIdCar(carId);  
	    if (carDto == null)   
	        // Tạo một thông báo lỗi khi không tìm thấy xe  
	       
	        return ResponseEntity.notFound().build();
	    
	    return ResponseEntity.ok(carDto);  
	}
	
	 @GetMapping("/car/bookings/{userId}")  
	    public ResponseEntity<List<BookCarDTO>> getBookingByUserid(@PathVariable Long userId) {  
	        List<BookCarDTO> bookings = customerServiceImpl.getBookingByUserid(userId);  
	        
	        if (bookings == null || bookings.isEmpty()) {  
	            // Nếu không tìm thấy đặt chỗ, trả về mã 404 Not Found với một danh sách rỗng  
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());  
	        }  
	        
	        // Nếu tìm thấy đặt chỗ, trả về danh sách cùng với mã 200 OK  
	        return ResponseEntity.ok(bookings);  
	    }  
	
	  
	  @PostMapping("/car/search")  
	  public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {  
	      // Kiểm tra nếu searchCarDto là null  
	      if (searchCarDto == null) {  
	          return ResponseEntity.badRequest().body("Search criteria cannot be null");  
	      }  

	      // Thực hiện tìm kiếm  
	      CarDtoListDTO carDtoListDTO = customerServiceImpl.searchCar(searchCarDto);   

	      // Kiểm tra nếu danh sách kết quả là null hoặc rỗng  
	      if (carDtoListDTO == null || carDtoListDTO.getCarDtolist().isEmpty()) {  
	          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found matching the criteria");  
	      }  

	      // Nếu tìm thấy xe, trả về danh sách xe với mã thành công 200  
	      return ResponseEntity.ok(carDtoListDTO.getCarDtolist());  
	  }
	  
}
