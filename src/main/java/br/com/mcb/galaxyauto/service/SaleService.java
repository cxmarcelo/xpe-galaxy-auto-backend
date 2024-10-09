package br.com.mcb.galaxyauto.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mcb.galaxyauto.dto.SaleCreateDto;
import br.com.mcb.galaxyauto.dto.SaleUpdateDto;
import br.com.mcb.galaxyauto.entities.SaleEntity;
import jakarta.validation.Valid;

public interface SaleService {

	Page<SaleEntity> findAll(Pageable pageable);

	SaleEntity findById(UUID saleId);

	SaleEntity approveSale(UUID saleId);

	SaleEntity rejectSale(UUID saleId);

	SaleEntity saveSale(@Valid SaleCreateDto saleCreateDto);

	SaleEntity update(UUID saleId, SaleUpdateDto saleUpdateDto);

}
