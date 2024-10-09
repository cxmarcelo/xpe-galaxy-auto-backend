package br.com.mcb.galaxyauto.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mcb.galaxyauto.dto.SaleCreateDto;
import br.com.mcb.galaxyauto.dto.SaleUpdateDto;
import br.com.mcb.galaxyauto.entities.CarEntity;
import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.enums.CarStatusEnum;
import br.com.mcb.galaxyauto.enums.SaleStatusEnum;
import br.com.mcb.galaxyauto.exceptions.DataIntegrityException;
import br.com.mcb.galaxyauto.exceptions.ObjectNotFoundException;
import br.com.mcb.galaxyauto.repositories.CarRepository;
import br.com.mcb.galaxyauto.repositories.SaleRepository;
import br.com.mcb.galaxyauto.service.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private CarRepository carRepository;

	@Override
	public SaleEntity findById(UUID id) {
		SaleEntity saleEntity = saleRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Venda não encontrada. Id: " + id));
		return saleEntity;
	}

	@Override
	public Page<SaleEntity> findAll(Pageable pageable) {
		return saleRepository.findAll(pageable);
	}

	@Override
	public SaleEntity saveSale(SaleCreateDto saleCreateDto) {
		CarEntity carEntity = carRepository.findById(saleCreateDto.getCarId())
				.orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado. Id: " + saleCreateDto.getCarId()));

		if(!carEntity.getStatus().equals(CarStatusEnum.AVAILABLE)) {
			throw new DataIntegrityException("Carro não está disponível.");
		}

		carEntity.setStatus(CarStatusEnum.SALES_PROCESS);

		SaleEntity saleEntity = new SaleEntity();
		saleEntity.setCar(carEntity);
		saleEntity.setClientName(saleCreateDto.getClientName());
		saleEntity.setClientCpfOrCnpj(saleCreateDto.getClientCpfOrCnpj());

		saleEntity.setSellerId(UUID.fromString("a7975941-55bd-4904-9e1b-4e90b026cb6b"));
		saleEntity.setSellerName("Marcelo Mock");

		saleEntity.setStatus(SaleStatusEnum.PENDENT);

		//TODO
		saleEntity.setCommission(new BigDecimal(100));


		return saleRepository.save(saleEntity);
	}

	@Override
	public SaleEntity update(UUID saleId, SaleUpdateDto saleUpdateDto) {
		SaleEntity saleEntity = findById(saleId);

		if(!SaleStatusEnum.PENDENT.equals(saleEntity.getStatus())) {
			throw new DataIntegrityException("Não é possível editar uma venda com status diferente de pendente.");
		}


		saleEntity.setClientName(saleUpdateDto.getClientName());
		saleEntity.setClientCpfOrCnpj(saleUpdateDto.getClientCpfOrCnpj());
		saleEntity.setCommission(saleUpdateDto.getCommission());
		
		return saleRepository.save(saleEntity);
	}

	@Override
	public SaleEntity approveSale(UUID saleId) {
		SaleEntity saleEntity = findById(saleId);

		if(!SaleStatusEnum.PENDENT.equals(saleEntity.getStatus())) {
			throw new DataIntegrityException("Não é possível aprovar uma venda com status diferente de pendente.");
		}

		saleEntity.getCar().setStatus(CarStatusEnum.SOLD);
		saleEntity.setStatus(SaleStatusEnum.APPROVED);
		return saleRepository.save(saleEntity);
	}

	@Override
	public SaleEntity rejectSale(UUID saleId) {
		SaleEntity saleEntity = findById(saleId);

		if(!SaleStatusEnum.PENDENT.equals(saleEntity.getStatus())) {
			throw new DataIntegrityException("Não é possível rejeitar uma venda com status diferente de pendente.");
		}

		//TODO check if will update
		saleEntity.getCar().setStatus(CarStatusEnum.AVAILABLE);
		saleEntity.setStatus(SaleStatusEnum.REJECTED);
		return saleRepository.save(saleEntity);
	}


}