package com.example.car.service.admin;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.mapping.Collection;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.example.car.DTO.BookCarDTO;
import com.example.car.DTO.CarDTO;
import com.example.car.DTO.CarDtoListDTO;
import com.example.car.DTO.SearchCarDto;
import com.example.car.entity.BookCar;
import com.example.car.entity.Car;
import com.example.car.enums.BookCarStatus;
import com.example.car.resposie.Bookcarresposie;
import com.example.car.resposie.CarRepository;

@Service
public class AdminServicelmpl  implements AdminService{
	
	private final CarRepository carReposie;
	
	private final Bookcarresposie bookcarresposie;
	
	



	public AdminServicelmpl(CarRepository carReposie, Bookcarresposie bookcarresposie) {
		super();
		this.carReposie = carReposie;
		this.bookcarresposie = bookcarresposie;
	}
	// phương thức này kiêm tra bên API nó có thành công hay không để dễ xử lý login thông báo cho người dùng 
	public boolean  postCar(CarDTO carDto) throws IOException {
	
	    try {
	    	Car car = new Car();
			car.setImage(carDto.getImage().getBytes());
			car.setBrand(carDto.getBrand());   
			car.setName(carDto.getName());
		    car.setType(carDto.getType());  
	    	car.setTransmission(carDto.getTransmission());    
			car.setColor(carDto.getColor());  
			car.setYear(carDto.getYear());  
			car.setPrice(carDto.getPrice());  
			car.setDescription(carDto.getDescription());  
	        carReposie.save(car);  // Giả sử carRepository là một repo để lưu car vào cơ sở dữ liệu  
	        return true; // Thành công  
	    } catch (Exception e) {  
	        // Xử lý lỗi nếu cần  
	        return false; // Thất bại  
	    }  
	}
    // phương thức lât  tất cả thông tin tử CarDTO
	public List<CarDTO> getAllCars(){
		return carReposie.findAll().stream().map(Car::getCarDTO).collect(Collectors.toList());
	}

  // xóa sản phẩm trên hệ thống 
	@Override
	public void deleteCar(Long id) {
		
		carReposie.deleteById(id);
	}




	@Override
	public CarDTO getcarById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public boolean updateCar(Long id, CarDTO carDto)  {
		  Optional<Car> optionalCar = carReposie.findById(id);  
		    if (optionalCar.isPresent()) {  
		        Car existingCar = optionalCar.get();  
		        
		        // Kiểm tra và cập nhật image nếu không null  
		        if (carDto.getImage() != null) {  
		            try {
						existingCar.setImage(carDto.getImage().getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		        }  

		        // Cập nhật các thuộc tính khác  
		        existingCar.setPrice(carDto.getPrice());  
		        existingCar.setYear(carDto.getYear());  
		        existingCar.setType(carDto.getType());  
		        existingCar.setDescription(carDto.getDescription());  
		        existingCar.setTransmission(carDto.getTransmission());  
		        existingCar.setColor(carDto.getColor());  
		        existingCar.setName(carDto.getName());  
		        existingCar.setBrand(carDto.getBrand());  
		        
		        // Lưu thay đổi vào cơ sở dữ liệu  
		        carReposie.save(existingCar);  
		        return true;  
		    } else {  
		        return false; // Không tìm thấy chiếc xe  
		    }  
	}
	@Override
	public List<BookCarDTO> getBooking() {
		return bookcarresposie.findAll().stream().map( BookCar :: getBookACarDto ).collect(Collectors.toList());
	}
	@Override
	public boolean changeBookingStatus(Long bookingId, String status) {
		Optional<BookCar> optionalbooking   = bookcarresposie.findById(bookingId);
		
		if (optionalbooking.isPresent()){

			BookCar extingBookingCar = optionalbooking.get();
			
			if (Objects.equals(status, "Approve")) {  
	            extingBookingCar.setBookCarStatus(BookCarStatus.APPROVED); // A cho Approved  
	        } else {  
	            extingBookingCar.setBookCarStatus(BookCarStatus.PENDING); // R cho Rejected (hoặc trạng thái khác phù hợp)  
	            
	            bookcarresposie.save(extingBookingCar);
	            
	            
	            return true;
	        }  

	}
		return false;



	}
	@Override
	public CarDtoListDTO searchCar(SearchCarDto searchCarDto) {
		 Car car = new Car();  
		    car.setBrand(searchCarDto.getBrand());  
		    car.setType(searchCarDto.getType());  
		    car.setTransmission(searchCarDto.getTransmission());  
		    car.setColor(searchCarDto.getColor());  
             //  để tạo ra một bộ lọc cho phép thực hiện tìm kiếm dựa trên các thuộc tính của đối tượng 
		    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()  
		        // 	withMatcher	Các phương thức withMatcher cho phép cấu hình các điều kiện tìm kiếm, trong trường hợp này
		        .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())  
		        .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())  
		        .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())  
		        .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());  
             // cho phép tạo một mẫu tìm kiếm dựa trên đối tượng car và bộ lọc exampleMatcher
		    Example<Car> carExample = Example.of(car, exampleMatcher);  
		    // để truy vấn cơ sở dữ liệu và lấy danh sách các đối tượng Car
		    List<Car> carList = carReposie.findAll(carExample);  

		    CarDtoListDTO carDtoListDto = new CarDtoListDTO();  
		    carDtoListDto.setCarDtolist(carList.stream().map(Car:: getCarDTO).collect(Collectors.toList()));  
		    
		    return carDtoListDto; 
	}

	
}
