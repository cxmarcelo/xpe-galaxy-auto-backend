package br.com.mcb.galaxyauto.enums;

public enum SaleStatusEnum {

	PENDENT("PENDENT"),
	REJECTED("REJECTED"),
	APPROVED("APPROVED"),
	;

	private String code;

	private SaleStatusEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static SaleStatusEnum toEnum(String code) {
		if (code == null) {
			return null;
		}

		for (SaleStatusEnum gen : SaleStatusEnum.values()) {
			if (code.equals(gen.getCode())) {
				return gen;
			}
		}

		throw null;
	}
}
