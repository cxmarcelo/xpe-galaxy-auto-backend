package br.com.mcb.galaxyauto.service.xls;

public enum CellStyleEnum {

	TITLE("TITLE"),
	DEFAULT("DEFAULT"),
	DECIMAL_DEFAULT("DECIMAL_DEFAULT"),
	CURRENCY_US("CURRENCY_US"),
	CURRENCY_BRL("CURRENCY_BRL"),
	DATE_DEFAULT("DATE_DEFAULT"),
	INTEGER_DEFAULT("INTEGER_DEFAULT")
	;

	private String code;

	private CellStyleEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

	public static CellStyleEnum toEnum(String code) {
		if (code == null) {
			return null;
		}

		for (CellStyleEnum cellStyle : CellStyleEnum.values()) {
			if (code.equals(cellStyle.getCode())) {
				return cellStyle;
			}
		}

		throw null;
	}

}
