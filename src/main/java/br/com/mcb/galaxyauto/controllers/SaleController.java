package br.com.mcb.galaxyauto.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcb.galaxyauto.dto.SaleCreateDto;
import br.com.mcb.galaxyauto.dto.SaleDto;
import br.com.mcb.galaxyauto.dto.SaleUpdateDto;
import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = {"Authorization", "Content-Type", "Accept"})
public class SaleController {

	@Autowired
	private SaleService saleService;

	@Operation(summary = "Search all sales", description = "Returns a page of sales.")
	@GetMapping
	public ResponseEntity<Page<SaleDto>> getAllSales( 
			@PageableDefault(page = 0, size = 24, sort = "createDate", direction = Direction.ASC) Pageable pageable) {
		Page<SaleEntity> page = saleService.findAll(pageable);
		Page<SaleDto> pageDto = page.map(saleEntity -> new SaleDto(saleEntity));
		return ResponseEntity.ok().body(pageDto);
	}

	@Operation(summary = "Search all sales", description = "Returns a sale.")
	@GetMapping("/{saleId}")
	public ResponseEntity<SaleDto> getOneSale(@PathVariable UUID saleId) {
		SaleEntity saleEntity = saleService.findById(saleId);
		SaleDto saleDto = new SaleDto(saleEntity);
		return ResponseEntity.ok().body(saleDto);
	}

	@Operation(summary = "Creates a sale.", description = "Creates a new sale with an available car.")
	@PostMapping
	public ResponseEntity<SaleDto> saveSale(@Valid @RequestBody SaleCreateDto saleCreateDto) {
		String username = getUsernameFromToken();
		SaleEntity saleEntity = saleService.saveSale(saleCreateDto, username);
		SaleDto saleDto = new SaleDto(saleEntity);
		return ResponseEntity.ok().body(saleDto);
	}
	
	@Operation(summary = "Update sale", description = "Updates and returns a sale.")
	@PutMapping("/{saleId}")
	public ResponseEntity<SaleDto> updateSale(@PathVariable UUID saleId, @Valid @RequestBody SaleUpdateDto saleUpdateDto) {
		SaleEntity saleEntity = saleService.update(saleId, saleUpdateDto);
		SaleDto saleDto = new SaleDto(saleEntity);
		return ResponseEntity.ok().body(saleDto);
	}

	@Operation(summary = "Approve sale", description = "Approves and return a sale.")
	@PostMapping("/approve/{saleId}")
	public ResponseEntity<SaleDto> approveSale(@PathVariable UUID saleId) {
		SaleEntity saleEntity = saleService.approveSale(saleId);
		SaleDto saleDto = new SaleDto(saleEntity);
		return ResponseEntity.ok().body(saleDto);
	}

	@Operation(summary = "Reject sale", description = "Reject and return a sale.")
	@PostMapping("/reject/{saleId}")
	public ResponseEntity<SaleDto> rejectSale(@PathVariable UUID saleId) {
		SaleEntity saleEntity = saleService.rejectSale(saleId);
		SaleDto saleDto = new SaleDto(saleEntity);
		return ResponseEntity.ok().body(saleDto);
	}
	
    private String getUsernameFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaimAsString("cognito:username");
    }

}
