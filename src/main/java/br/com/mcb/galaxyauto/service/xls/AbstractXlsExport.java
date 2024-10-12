package br.com.mcb.galaxyauto.service.xls;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class AbstractXlsExport {
	
	public double formatBigDecimal(BigDecimal value) {
		if(value != null) {
			return value.doubleValue();
		} else {
			return 0;
		}
	}

	public void autoResize(Sheet sheet, int cols) {
		for (int col = 0; col < cols; col++) {
			sheet.autoSizeColumn(col);
		}
	}

	public void createCell(Row row, Integer col, CellStyle cellStyle, String value) {
		Cell cell = row.createCell(col);
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		if(value != null) {
			cell.setCellValue(value);
		}
	}

	public void createCell(Row row, int col, CellStyle cellStyle, Long value) {
		Cell cell = row.createCell(col);
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		if(value != null) {
			cell.setCellValue(value);
		}
	}

	public void createCell(Row row, int col, CellStyle cellStyle, Integer value) {
		Cell cell = row.createCell(col);
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		if(value != null) {
			cell.setCellValue(value);
		}
	}

	public void createCell(Row row, int col, CellStyle cellStyle, Boolean value) {
		Cell cell = row.createCell(col);
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		if(value != null) {
			cell.setCellValue(value);
		}
	}

	public void createCell(Row row, int col, CellStyle cellStyle, BigDecimal value) {
		Cell cell = row.createCell(col);
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		if(value != null) {
			cell.setCellValue(value.doubleValue());
		}
	}

	public void createCell(Row row, int col, CellStyle cellStyle, Date value) {
		Cell cell = row.createCell(col);
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		if(value != null) {
			cell.setCellValue(value);
		}
	}
}
