package br.com.mcb.galaxyauto.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarCreateUpdateDto {

	@NotBlank
	private String name;

	@NotBlank
	private String brand;

	@NotBlank
	private String description;

	@NotNull
	private BigDecimal price;

	//TODO
	@NotNull
	private String imageUrl;

	//TODO custom validation
	@NotBlank
	private String status;

}
