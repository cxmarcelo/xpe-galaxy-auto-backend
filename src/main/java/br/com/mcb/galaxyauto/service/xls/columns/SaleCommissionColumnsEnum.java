package br.com.mcb.galaxyauto.service.xls.columns;

public enum SaleCommissionColumnsEnum {

	ID("ID", "Id"),
	CAR_NAME("CAR_NAME", "Nome Carro"),
	CAR_PLATE("CAR_PLATE", "Placa"),
	CAR_PRICE("CAR_PRICE", "Preço"),
	SALLER_NAME("SALLER_NAME", "Vendedor"),
	COMMISSION("COMMISSION", "Comissão"),
	CREATE_DATE("CREATE_DATE", "Data Venda"),
	;

	private String code;
	private String title;

	private SaleCommissionColumnsEnum(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public String getCode() {
		return this.code;
	}

	public String getTitle() {
		return title;
	}

	public static SaleCommissionColumnsEnum toEnum(String code) {
		if (code == null) {
			return null;
		}

		for (SaleCommissionColumnsEnum saleEnum : SaleCommissionColumnsEnum.values()) {
			if (code.equals(saleEnum.getCode())) {
				return saleEnum;
			}
		}

		throw null;
	}

}
