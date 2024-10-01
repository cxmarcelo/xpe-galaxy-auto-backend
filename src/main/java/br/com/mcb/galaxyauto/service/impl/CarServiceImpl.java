package br.com.mcb.galaxyauto.service.impl;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mcb.galaxyauto.dto.CarCreateUpdateDto;
import br.com.mcb.galaxyauto.entities.CarEntity;
import br.com.mcb.galaxyauto.enums.CarStatusEnum;
import br.com.mcb.galaxyauto.exceptions.ObjectNotFoundException;
import br.com.mcb.galaxyauto.repositories.CarRepository;
import br.com.mcb.galaxyauto.service.CarService;
import jakarta.validation.Valid;

@Service
public class CarServiceImpl implements CarService {

	private CarRepository carRepository;

	@Override
	public Page<CarEntity> findAll(Pageable pageable) {
		return carRepository.findAll(pageable);
	}

	@Override
	public CarEntity findById(UUID carId) {
		return carRepository.findById(carId).orElseThrow(() -> new ObjectNotFoundException("Car not found. Id: " + carId));
	}

	@Override
	public CarEntity save(@Valid CarCreateUpdateDto carDto) {
		CarEntity carEntity = new CarEntity();
		BeanUtils.copyProperties(carDto, carEntity);
		carEntity.setStatus(CarStatusEnum.toEnum(carDto.getStatus()));
		return carRepository.save(carEntity);
	}

	@Override
	public CarEntity update(UUID carId, CarCreateUpdateDto carDto) {
		CarEntity carEntity = this.findById(carId);

		carEntity.setName(carDto.getName());
		carEntity.setBrand(carDto.getBrand());
		carEntity.setDescription(carDto.getDescription());
		carEntity.setPrice(carDto.getPrice());
		carEntity.setStatus(CarStatusEnum.toEnum(carDto.getStatus()));

		return carRepository.save(carEntity);
	}

	@Override
	public void delete(UUID carId) {
		CarEntity carEntity = findById(carId);
		carRepository.delete(carEntity);
	}

}
