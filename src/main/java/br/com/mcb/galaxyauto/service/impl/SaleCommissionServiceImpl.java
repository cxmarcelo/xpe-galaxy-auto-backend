package br.com.mcb.galaxyauto.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.repositories.SaleRepository;
import br.com.mcb.galaxyauto.service.SaleCommissionService;
import br.com.mcb.galaxyauto.service.xls.imports.SaleCommissionXlsImport;
import br.com.mcb.galaxyauto.specifications.SaleCommissionSpecification;

@Service
public class SaleCommissionServiceImpl implements SaleCommissionService {

	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private SaleCommissionXlsImport saleCommissionXlsImport;

	@Override
	public Page<SaleEntity> findAll(Pageable pageable) {
		return saleRepository.findAll(SaleCommissionSpecification.isApprovedSale(), pageable);
	}

	@Override
	public List<SaleEntity> uploadCommisionByXlsFile(MultipartFile file) {
		List<SaleEntity> saleEntityToUpdateList = saleCommissionXlsImport.getSalesInFile(file);
		Map<UUID, SaleEntity> saleEntityMap = saleEntityToUpdateList.stream().collect(Collectors.toMap(SaleEntity::getId, saleEntity -> saleEntity));

		List<SaleEntity> saleEntityList = saleRepository.findAllById(saleEntityMap.keySet());
		
		//I could add more validations here
		for (SaleEntity saleEntity : saleEntityList) {
			SaleEntity saleEntityToUpdate = saleEntityMap.get(saleEntity.getId());
			saleEntity.setCommission(saleEntityToUpdate.getCommission());
		}
		
		return saleRepository.saveAll(saleEntityList);
	}

}
