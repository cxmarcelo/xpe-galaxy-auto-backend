package br.com.mcb.galaxyauto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.mcb.galaxyauto.dto.SaleDto;
import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.service.SaleCommissionService;
import br.com.mcb.galaxyauto.service.xls.exports.SaleCommissionExport;

@RestController
@RequestMapping("/sales/commissions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SaleCommissionController {

	@Autowired
	private SaleCommissionService saleCommissionService;

	@Autowired 
	private SaleCommissionExport saleCommissionExport;

	@GetMapping
	public ResponseEntity<Page<SaleDto>> getAllSales( 
			@PageableDefault(page = 0, size = 24, sort = "createDate", direction = Direction.ASC) Pageable pageable) {
		Page<SaleEntity> page = saleCommissionService.findAll(pageable);
		Page<SaleDto> pageDto = page.map(saleEntity -> new SaleDto(saleEntity));
		return ResponseEntity.ok().body(pageDto);
	}

	@GetMapping("/export")
	public ResponseEntity<Resource> exportCommissions(
			@PageableDefault(page = 0, size = 24, sort = "createDate", direction = Direction.ASC) Pageable pageable
			) {
		Page<SaleEntity> page = saleCommissionService.findAll(pageable);

		InputStreamResource file = new InputStreamResource(this.saleCommissionExport.exportSaleCommission(page.getContent()));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "sale_commission.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				.body(file);
	}

	@PostMapping("/upload")
	public ResponseEntity<List<SaleDto>> uplaodCommissionByXls(@RequestParam("file") MultipartFile file) {
		List<SaleEntity> saleEntityList = saleCommissionService.uploadCommisionByXlsFile(file);
		List<SaleDto> saleDtoList = saleEntityList.stream().map(saleEntity -> new SaleDto(saleEntity)).toList();
		return ResponseEntity.ok().body(saleDtoList);

	}

}
