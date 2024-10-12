package br.com.mcb.galaxyauto.service;

import java.math.BigDecimal;

public interface CommissionCalculationService {

	public BigDecimal calculateCommission(BigDecimal carPrice);

}
