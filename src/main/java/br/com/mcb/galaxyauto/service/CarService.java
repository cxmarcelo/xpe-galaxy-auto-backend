package br.com.mcb.galaxyauto.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mcb.galaxyauto.dto.CarCreateDto;
import br.com.mcb.galaxyauto.dto.CarDto;
import br.com.mcb.galaxyauto.entities.CarEntity;
import jakarta.validation.Valid;

public interface CarService {

	Page<CarEntity> findAll(Pageable pageable);

	CarEntity findById(UUID carId);

	CarEntity update(UUID carId, @Valid CarDto carUpdateDto);
	
	CarEntity save(CarCreateDto carDto, String imageUrl);

	CarEntity updateImage(UUID carId, String imageUrl);

	void delete(UUID carId);

}
