package br.com.mcb.galaxyauto.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.mcb.galaxyauto.enums.CarStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class CarEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;
	private String brand;
	private String description;
	private BigDecimal price;
	private String imageUrl;
	private String status;

	private LocalDateTime createDate;
	private LocalDateTime lastUpdate;


	@PrePersist
	public void prePersist() {
		createDate = LocalDateTime.now();
		lastUpdate = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		lastUpdate = LocalDateTime.now();
	}

	public CarStatusEnum getStatus() {
		return CarStatusEnum.toEnum(status);
	}

	public void setStatus(CarStatusEnum status) {
		this.status = status.getCode();
	}

}
