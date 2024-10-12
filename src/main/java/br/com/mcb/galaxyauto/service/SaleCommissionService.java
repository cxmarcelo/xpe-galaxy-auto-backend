package br.com.mcb.galaxyauto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import br.com.mcb.galaxyauto.entities.SaleEntity;

public interface SaleCommissionService {

	Page<SaleEntity> findAll(Pageable pageable);

	List<SaleEntity> uploadCommisionByXlsFile(MultipartFile file);

}
