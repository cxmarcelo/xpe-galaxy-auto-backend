package br.com.mcb.galaxyauto.service.xls;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class CellStyleXls {
	
	private Workbook workbook;
	private Map<CellStyleEnum, CellStyle> cellStyleList;
	private Map<String, XSSFFont> fontCache;

	public CellStyleXls(Workbook workbook) {
		this.workbook = workbook;
		this.cellStyleList = new HashMap<CellStyleEnum, CellStyle>();
		this.fontCache = new HashMap<String, XSSFFont>();

		createTitleStyle();
		createNormalStyle();
		createNormalDecimalStyle();
		createNormalIntegerStyle();
		createNormalDateStyle();
		createNormalCurrencyStyle();
		createNormalCurrencyWithRealSignStyle();
	}

	private void createNormalStyle() {
		CellStyle cellStyle  = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyleList.put(CellStyleEnum.DEFAULT, cellStyle);
	}

	private void createNormalDecimalStyle() {
		CellStyle cellStyle  = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.0000"));
		cellStyleList.put(CellStyleEnum.DECIMAL_DEFAULT, cellStyle);
	}

	private void createNormalCurrencyStyle() {
		CellStyle cellStyle  = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		cellStyleList.put(CellStyleEnum.CURRENCY_US, cellStyle);
	}

	private void createNormalCurrencyWithRealSignStyle() {
		CellStyle cellStyle  = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("R$#,##0.00"));
		cellStyleList.put(CellStyleEnum.CURRENCY_BRL, cellStyle);
	}

	private void createNormalDateStyle() {
		CellStyle cellStyle  = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		cellStyleList.put(CellStyleEnum.DATE_DEFAULT, cellStyle);
	}

	private void createNormalIntegerStyle() {
		CellStyle cellStyle  = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#0"));
		cellStyleList.put(CellStyleEnum.INTEGER_DEFAULT, cellStyle);
	}

	private void createTitleStyle() {
		CellStyle cellStyle  = workbook.createCellStyle();
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFont(titleFont());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyleList.put(CellStyleEnum.TITLE, cellStyle);
	}

	protected XSSFFont titleFont() {
		return createFont("title", true, IndexedColors.BLACK.index);
	}

	private XSSFFont createFont(String name, boolean bold, short color) {
		XSSFFont xssfFont = fontCache.get(name);
		if (xssfFont == null) {
			xssfFont = (XSSFFont) workbook.createFont();
			xssfFont.setBold(bold);
			xssfFont.setColor(color);
		}
		return xssfFont;
	}

	public Map<CellStyleEnum, CellStyle> getCellStyleCache() {
		return cellStyleList;
	}

	public Map<String, XSSFFont> getFontCache() {
		return fontCache;
	}
}
