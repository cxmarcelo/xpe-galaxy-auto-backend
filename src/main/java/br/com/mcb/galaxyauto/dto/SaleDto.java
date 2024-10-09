package br.com.mcb.galaxyauto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.enums.SaleStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDto {

	private UUID id;
	private UUID sellerId;
	private String sellerName;
	private CarDto car;
	private String clientName;
	private String clientCpfOrCnpj;
	private SaleStatusEnum status;
	private BigDecimal commission;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdate;

	public SaleDto(SaleEntity saleEntity) {
		BeanUtils.copyProperties(saleEntity, this);

		if(saleEntity.getCar() != null) {
			this.car = new CarDto(saleEntity.getCar());
		}
	}

}
