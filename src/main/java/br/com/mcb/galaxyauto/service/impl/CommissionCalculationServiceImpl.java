package br.com.mcb.galaxyauto.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import br.com.mcb.galaxyauto.service.CommissionCalculationService;

@Service
public class CommissionCalculationServiceImpl implements CommissionCalculationService {

	private BigDecimal commissionPercentage = new BigDecimal(5);

	@Override
	public BigDecimal calculateCommission(BigDecimal carPrice) {
		BigDecimal factor = commissionPercentage.divide(BigDecimal.valueOf(100));
		BigDecimal comissao = carPrice.multiply(factor);
		return comissao.setScale(2, RoundingMode.HALF_UP);
	}

}
