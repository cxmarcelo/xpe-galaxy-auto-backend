package br.com.mcb.galaxyauto.service.impl;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mcb.galaxyauto.dto.CarCreateDto;
import br.com.mcb.galaxyauto.dto.CarDto;
import br.com.mcb.galaxyauto.entities.CarEntity;
import br.com.mcb.galaxyauto.enums.CarStatusEnum;
import br.com.mcb.galaxyauto.exceptions.DataIntegrityException;
import br.com.mcb.galaxyauto.exceptions.ObjectNotFoundException;
import br.com.mcb.galaxyauto.repositories.CarRepository;
import br.com.mcb.galaxyauto.service.CarService;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;

	@Override
	public Page<CarEntity> findAll(Pageable pageable) {
		return carRepository.findAll(pageable);
	}

	@Override
	public CarEntity findById(UUID carId) {
		return carRepository.findById(carId).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado. Id: " + carId));
	}

	@Override
	public CarEntity save(CarCreateDto carDto, String imageUrl) {
		CarEntity carEntity = new CarEntity();
		BeanUtils.copyProperties(carDto, carEntity);
		carEntity.setStatus(CarStatusEnum.toEnum(carDto.getStatus()));
		carEntity.setImageUrl(imageUrl);

		return carRepository.save(carEntity);
	}

	@Override
	public CarEntity update(UUID carId, CarDto carDto) {
		CarEntity carEntity = this.findById(carId);

		if(!(CarStatusEnum.AVAILABLE.equals(carEntity.getStatus()) 
				|| CarStatusEnum.UNAVAILABLE.equals(carEntity.getStatus()))) {
			throw new DataIntegrityException("Não é possível editar carros com status diferente de Disponível ou Indisponível");
		}

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

	@Override
	public CarEntity updateImage(UUID carId, String imageUrl) {
		CarEntity carEntity = findById(carId);
		carEntity.setImageUrl(imageUrl);
		return carRepository.save(carEntity);
	}

}
