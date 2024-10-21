package br.com.mcb.galaxyauto.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.mcb.galaxyauto.enums.SaleStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class SaleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
    @JoinColumn(name = "user_id")
	private UserEntity seller;

	@ManyToOne
	@JoinColumn(name = "car_id", nullable = false)
	private CarEntity car;

	@Column(nullable = false)
	private String clientName;

	@Column(nullable = false)
	private String clientCpfOrCnpj;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private BigDecimal commission;

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

	public SaleStatusEnum getStatus() {
		return SaleStatusEnum.toEnum(this.status);
	}

	public void setStatus(SaleStatusEnum status) {
		this.status = status.getCode();
	}


}
