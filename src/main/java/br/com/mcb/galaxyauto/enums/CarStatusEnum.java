package br.com.mcb.galaxyauto.enums;

public enum CarStatusEnum {

	AVAILABLE("AVAILABLE"),
	UNAVAILABLE("UNAVAILABLE"),
	SALES_PROCESS("SALES_PROCESS"),
	SOLD("SOLD"),
	;

	private String code;

	private CarStatusEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static CarStatusEnum toEnum(String code) {
		if (code == null) {
			return null;
		}

		for (CarStatusEnum gen : CarStatusEnum.values()) {
			if (code.equals(gen.getCode())) {
				return gen;
			}
		}

		throw null;
	}
}
