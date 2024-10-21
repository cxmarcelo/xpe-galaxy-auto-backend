package br.com.mcb.galaxyauto.controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.fasterxml.jackson.annotation.JsonView;

import br.com.mcb.galaxyauto.dto.CarCreateDto;
import br.com.mcb.galaxyauto.dto.CarDto;
import br.com.mcb.galaxyauto.entities.CarEntity;
import br.com.mcb.galaxyauto.exceptions.FileUploadFailException;
import br.com.mcb.galaxyauto.service.CarService;
import br.com.mcb.galaxyauto.service.S3Service;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization")
public class CarController {

	@Autowired
	private CarService carService;

	@Autowired 
	private S3Service s3Service;

	@GetMapping
	public ResponseEntity<Page<CarDto>> getAllCars( 
			@PageableDefault(page = 0, size = 24, sort = "createDate", direction = Direction.ASC) Pageable pageable) {
		Page<CarEntity> page = carService.findAll(pageable);
		Page<CarDto> pageDto = page.map(carEntity -> new CarDto(carEntity));
		return ResponseEntity.ok().body(pageDto);
	}

	@GetMapping("/{carId}")
	public ResponseEntity<CarDto> getOneCar(@PathVariable UUID carId) {
		CarEntity carEntity = carService.findById(carId);
		CarDto carDto = new CarDto(carEntity);
		return ResponseEntity.ok().body(carDto);
	}

	@PostMapping
	public ResponseEntity<CarDto> saveCar(@Valid @ModelAttribute CarCreateDto carCreateDto, @RequestParam("image") MultipartFile image) {
		try {
			String imageUrl = s3Service.uploadFile(image);

			CarEntity carEntity = carService.save(carCreateDto, imageUrl);
			
			CarDto carDto = new CarDto(carEntity);
			return ResponseEntity.ok().body(carDto);

		} catch (IOException e) {
			throw new FileUploadFailException("Failed to update image");
		}
	}

	@PutMapping("/{carId}")
	public ResponseEntity<CarDto> updateCar(@PathVariable UUID carId, @Validated @RequestBody @JsonView(CarDto.CarView.UpdateCar.class) CarDto carUpdateDto) {
		CarEntity carEntity = carService.update(carId, carUpdateDto);
		CarDto carDto = new CarDto(carEntity);
		return ResponseEntity.ok().body(carDto);
	}

	@PutMapping("/{carId}/image")
	public ResponseEntity<CarEntity> updateCarImage(@PathVariable UUID id, @RequestParam("file") MultipartFile image) {
		this.carService.findById(id);

		try {
			String imageUrl = s3Service.uploadFile(image);
			CarEntity carEntity = carService.updateImage(id, imageUrl);
			return ResponseEntity.ok().body(carEntity);
		} catch (Exception e) {
			throw new FileUploadFailException("Failed to update image");
		}
	}

	@DeleteMapping("/{carId}")
	public ResponseEntity<Void> deleteCar(@PathVariable UUID carId) {
		carService.delete(carId);
		return ResponseEntity.ok().build();
	}


}
