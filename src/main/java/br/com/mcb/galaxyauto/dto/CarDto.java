package br.com.mcb.galaxyauto.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarDto {

	public interface CarView {
		public static interface PostCar {}
		public static interface UpdateCar {}
	}

	@NotBlank(groups = {CarView.PostCar.class, CarView.UpdateCar.class})
	@JsonView({CarView.PostCar.class, CarView.UpdateCar.class})
	private String name;

	@NotBlank(groups = {CarView.PostCar.class, CarView.UpdateCar.class})
	@JsonView({CarView.PostCar.class, CarView.UpdateCar.class})
	private String brand;
	
	@NotBlank(groups = {CarView.PostCar.class, CarView.UpdateCar.class})
	@JsonView({CarView.PostCar.class, CarView.UpdateCar.class})
	private String plate;

	@NotBlank(groups = {CarView.PostCar.class, CarView.UpdateCar.class})
	@JsonView({CarView.PostCar.class, CarView.UpdateCar.class})
	private String description;

	@NotNull(groups = {CarView.PostCar.class, CarView.UpdateCar.class})
	@JsonView({CarView.PostCar.class, CarView.UpdateCar.class})
	private BigDecimal price;

	@NotBlank(groups = {CarView.PostCar.class, CarView.UpdateCar.class})
	@JsonView({CarView.PostCar.class, CarView.UpdateCar.class})
	private String status;
	
	private String imageUrl;

}
