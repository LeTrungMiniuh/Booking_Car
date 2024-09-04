package com.example.car.controlller;

import java.io.IOException;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.car.DTO.BookCarDTO;
import com.example.car.DTO.CarDTO;
import com.example.car.DTO.CarDtoListDTO;
import com.example.car.DTO.SearchCarDto;
import com.example.car.service.admin.AdminService;
import com.example.car.service.admin.AdminServicelmpl;

@RestController
@RequestMapping("api/admin")
public class AdminController {
	
	private final AdminServicelmpl adminServicelmpl;
	

	public AdminController(AdminServicelmpl adminServicelmpl) {
		super();
		this.adminServicelmpl = adminServicelmpl;
	}


	// ánh xạ dữ liệu từ form từ HTML để truyền tải dữ liệu 
	  @PostMapping("/car")  
	  public ResponseEntity<?> postCar(@ModelAttribute CarDTO carDto) throws IOException {  
	      boolean success = adminServicelmpl.postCar(carDto);  
	      if (success) {  
	          return ResponseEntity.status(HttpStatus.CREATED).build();  
	      } else {  
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  
	      }  
	  }
	
     // lấy tất  cả thông tin của xe 
	  @GetMapping("/getallcar")  
	  public ResponseEntity<?> getAllCar()   {
		  
		  try {  
		        List<CarDTO> cars = adminServicelmpl.getAllCars(); // Giả định rằng adminService.getAllCars() trả về danh sách xe ô tô  

		        if (cars.isEmpty()) {  
		            // Nếu danh sách rỗng, trả về thông điệp với mã trạng thái 204 (No Content)  
		            return ResponseEntity.noContent().build();  
		        }  

		        // Nếu có dữ liệu, trả về danh sách xe với mã trạng thái 200 (OK)  
		        return ResponseEntity.ok(cars);  
		        
		    } catch (Exception e) {  
		        // Xử lý lỗi, có thể là lỗi kết nối cơ sở dữ liệu, ngoại lệ không mong muốn, v.v.  
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)  
		                             .body("Đã xảy ra lỗi khi lấy danh sách xe: " + e.getMessage());  
		    }  
		
	}
	  
	  // xóa 1 phẩn sản phẩm 
	  
	  @DeleteMapping("/xoacar/{id}")  
	  public ResponseEntity<String> deleteCar(@PathVariable Long id) {  
		  adminServicelmpl.deleteCar(id);  
	      return ResponseEntity.ok("Xóa xe với ID " + id + " thành công.");  
	  }
	  // lấy tât cả thông tin từ các danh sachs 
	  
	  @GetMapping("/car/{id}")  
	  public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {  
	      CarDTO carDto = adminServicelmpl.getcarById(id);  
	      return ResponseEntity.ok(carDto);  
	  }
	  
	  // lấy 1 sản phẩm bằng id từ danh sahcs List
	  
	  @PutMapping("/car/{carId}")  
	  public ResponseEntity<Void> updateCar(@PathVariable Long carId, @ModelAttribute CarDTO carDto) throws IOException {  
	      try {  
	          boolean success = adminServicelmpl.updateCar(carId, carDto);  
	          if (success) {  
	              return ResponseEntity.status(HttpStatus.OK).build();  
	          } else {  
	              return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Không tìm thấy chiếc xe để cập nhật  
	          }  
	      } catch (Exception e) {  
	    	  // Xử lý lỗi, có thể là lỗi kết nối cơ sở dữ liệu, ngoại lệ không mong muốn  
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  
	      }  
	  }
	  
	  
	  
	  @GetMapping("/car/getbookinId")  
	  public ResponseEntity<List<BookCarDTO>> getBooking() {  
	   
	      return ResponseEntity.ok(adminServicelmpl.getBooking());
	  }
	  
	  
	  @GetMapping("/car/booking/{bookingId}/{status}")  
	  public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String  status)  {  
	       
	          boolean success = adminServicelmpl.changeBookingStatus(bookingId, status);  
	          if (success) 
	              return ResponseEntity.ok().build(); 
	              
	               return ResponseEntity.notFound().build();
	         
	  }
	  
	  
	  @PostMapping("/car/search")  
	  public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {  
	      // Kiểm tra nếu searchCarDto là null  
	      if (searchCarDto == null) {  
	          return ResponseEntity.badRequest().body("Search criteria cannot be null");  
	      }  

	      // Thực hiện tìm kiếm  
	      CarDtoListDTO carDtoListDTO = adminServicelmpl.searchCar(searchCarDto);   

	      // Kiểm tra nếu danh sách kết quả là null hoặc rỗng  
	      if (carDtoListDTO == null || carDtoListDTO.getCarDtolist().isEmpty()) {  
	          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars found matching the criteria");  
	      }  

	      // Nếu tìm thấy xe, trả về danh sách xe với mã thành công 200  
	      return ResponseEntity.ok(carDtoListDTO.getCarDtolist());  
	  }
	  
	   
}
