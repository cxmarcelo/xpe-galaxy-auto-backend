package br.com.mcb.galaxyauto.controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.mcb.galaxyauto.dto.CarCreateDto;
import br.com.mcb.galaxyauto.dto.CarDto;
import br.com.mcb.galaxyauto.entities.CarEntity;
import br.com.mcb.galaxyauto.service.CarService;
import br.com.mcb.galaxyauto.service.S3Service;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarController {

	@Autowired
	private CarService carService;
	
	@Autowired S3Service s3Service;

	@GetMapping
	public ResponseEntity<Page<CarEntity>> getAllCars( 
			@PageableDefault(page = 0, size = 24, sort = "createDate", direction = Direction.ASC) Pageable pageable) {
		Page<CarEntity> page = carService.findAll(pageable);
		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{carId}")
	public ResponseEntity<CarEntity> getOneCar(@PathVariable UUID carId) {
		CarEntity carEntity = carService.findById(carId);
		return ResponseEntity.ok().body(carEntity);
	}

	@PostMapping
	public ResponseEntity<Object> saveCar(@Valid @ModelAttribute CarCreateDto carDto, @RequestParam("image") MultipartFile image) {
		//TODO ON ERROR
		
        try {
            String imageUrl = s3Service.uploadFile(image);

    		CarEntity carEntity = carService.save(carDto, imageUrl);
    		return ResponseEntity.ok().body(carEntity);
    		
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@PutMapping("/{carId}")
	public ResponseEntity<CarEntity> updateCar(@PathVariable UUID carId, @Valid @RequestBody CarDto carDto) {
		CarEntity carEntity = carService.update(carId, carDto);
		return ResponseEntity.ok().body(carEntity);
	}

	@DeleteMapping("/{carId}")
	public ResponseEntity<Void> deleteCar(@PathVariable UUID carId) {
		carService.delete(carId);
		return ResponseEntity.ok().build();
	}


}
