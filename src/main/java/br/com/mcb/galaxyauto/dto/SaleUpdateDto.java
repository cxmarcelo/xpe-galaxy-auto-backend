package br.com.mcb.galaxyauto.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleUpdateDto {
	
	@NotBlank
	private String clientName;

	@NotBlank
	private String clientCpfOrCnpj;

	@NotNull
	private BigDecimal commission;

}
