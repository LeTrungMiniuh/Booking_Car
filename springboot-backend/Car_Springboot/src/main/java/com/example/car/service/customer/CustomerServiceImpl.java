package com.example.car.service.customer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.example.car.DTO.BookCarDTO;
import com.example.car.DTO.CarDTO;
import com.example.car.DTO.CarDtoListDTO;
import com.example.car.DTO.SearchCarDto;
import com.example.car.entity.BookCar;
import com.example.car.entity.Car;
import com.example.car.entity.User;
import com.example.car.enums.BookCarStatus;
import com.example.car.resposie.Bookcarresposie;
import com.example.car.resposie.CarRepository;
import com.example.car.resposie.UserRepository;


@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final  CarRepository carRepository;
 
	private final  UserRepository userRepository;
	
	private final  Bookcarresposie bookcarresposie;
	


	public CustomerServiceImpl(CarRepository carRepository, UserRepository userRepository,
			Bookcarresposie bookcarresposie) {
		super();
		this.carRepository = carRepository;
		this.userRepository = userRepository;
		this.bookcarresposie = bookcarresposie;
	}

	@Override
	public List<CarDTO> getAllCars() {
		return  carRepository.findAll().stream().map(Car :: getCarDTO).collect(Collectors.toList());
	}

	public boolean bookACar(BookCarDTO bookACarDto) {  
	    Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCardId());  
	    Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserid());  
	    
	    if (optionalCar.isPresent() && optionalUser.isPresent()) {  
	        Car existingCar = optionalCar.get();  
	        BookCar bookACar = new BookCar();  
	        bookACar.setUser(optionalUser.get());  
	        bookACar.setCar(existingCar);  
	        bookACar.setBookCarStatus(BookCarStatus.PENDING);  
	        
	        Long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();  
	        Long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);  
	        bookACar.setDays(days);  
	        bookACar.setPrice(existingCar.getPrice() * days);  
	        
	        bookcarresposie.save(bookACar);
	        
	        // Thêm logic để lưu bookACar vào cơ sở dữ liệu, nếu cần  
	        return true; // Hoặc false tùy vào việc lưu thành công hay không  
	    }  
	    return false; // Trả về false nếu không tìm thấy xe hoặc người dùng  
	}

	@Override
	public CarDTO getallIdCar(Long Carid) { 
	    Optional<Car> optionalCar = carRepository.findById(Carid);  
	    return optionalCar.map(Car::getCarDTO).orElse(null);  
	}

	@Override
	public List<BookCarDTO> getBookingByUserid(Long userId) {
	   
		   return bookcarresposie.findAllByUserId(userId).stream().map(BookCar ::getBookACarDto).collect(Collectors.toList());
			      
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
		    List<Car> carList = carRepository.findAll(carExample);  

		    CarDtoListDTO carDtoListDto = new CarDtoListDTO();  
		    carDtoListDto.setCarDtolist(carList.stream().map(Car:: getCarDTO).collect(Collectors.toList()));  
		    
		    return carDtoListDto; 
	}

	
	

}
