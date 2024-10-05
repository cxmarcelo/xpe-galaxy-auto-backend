package br.com.mcb.galaxyauto.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.mcb.galaxyauto.enums.CarStatusEnum;
import jakarta.persistence.Column;
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

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String brand;

	private String plate;
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	private String imageUrl;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private LocalDateTime createDate;

	@Column(nullable = false)
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
