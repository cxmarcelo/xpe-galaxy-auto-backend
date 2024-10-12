package br.com.mcb.galaxyauto.specifications;

import org.springframework.data.jpa.domain.Specification;

import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.enums.SaleStatusEnum;

public class SaleCommissionSpecification {


	public static Specification<SaleEntity> isApprovedSale() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), SaleStatusEnum.APPROVED.getCode());
	}


}
