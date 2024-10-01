package br.com.mcb.galaxyauto.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mcb.galaxyauto.dto.CarCreateUpdateDto;
import br.com.mcb.galaxyauto.entities.CarEntity;

public interface CarService {

	Page<CarEntity> findAll(Pageable pageable);

	CarEntity findById(UUID carId);

	CarEntity update(UUID carId, CarCreateUpdateDto carDto);

	CarEntity save(CarCreateUpdateDto carDto);

	void delete(UUID carId);

}
