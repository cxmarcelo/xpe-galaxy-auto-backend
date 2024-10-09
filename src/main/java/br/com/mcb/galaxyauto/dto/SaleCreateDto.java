package br.com.mcb.galaxyauto.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class SaleCreateDto {
	
	private UUID carId;
	private String clientName;
	private String clientCpfOrCnpj;

}
