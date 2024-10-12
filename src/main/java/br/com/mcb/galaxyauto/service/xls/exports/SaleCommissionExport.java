package br.com.mcb.galaxyauto.service.xls.exports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.exceptions.FileIOException;
import br.com.mcb.galaxyauto.exceptions.ObjectNotFoundException;
import br.com.mcb.galaxyauto.service.xls.AbstractXlsExport;
import br.com.mcb.galaxyauto.service.xls.CellStyleEnum;
import br.com.mcb.galaxyauto.service.xls.CellStyleXls;
import br.com.mcb.galaxyauto.service.xls.columns.SaleCommissionColumnsEnum;

@Service
public class SaleCommissionExport extends AbstractXlsExport {

	public ByteArrayInputStream exportSaleCommission(List<SaleEntity> positionList) {
		if(positionList.size() == 0) {
			throw new ObjectNotFoundException("Não foram encontrados registros para exportação.");
		}

		List<SaleCommissionColumnsEnum> columns = Arrays.asList(
				SaleCommissionColumnsEnum.ID,
				SaleCommissionColumnsEnum.COMMISSION,
				SaleCommissionColumnsEnum.CAR_NAME,
				SaleCommissionColumnsEnum.CAR_PLATE,
				SaleCommissionColumnsEnum.CAR_PRICE,
				SaleCommissionColumnsEnum.SALLER_NAME,
				SaleCommissionColumnsEnum.CREATE_DATE
				);

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CellStyleXls cellStyle = new CellStyleXls(workbook);
			createSheet(workbook, cellStyle, columns, positionList);

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new FileIOException("Falha interna ao exportar planilha excel.");
		}
	}


	private void createSheet(Workbook workbook, CellStyleXls cellStyle, List<SaleCommissionColumnsEnum> columns, List<SaleEntity> positionList) {
		Sheet sheet = workbook.createSheet();

		Row headerRow = sheet.createRow(0);
		for (int col = 0; col < columns.size(); col++) {
			createCell(headerRow, col, cellStyle.getCellStyleCache().get(CellStyleEnum.TITLE), columns.get(col).getTitle());
		}

		int rowIdx = 1;
		for (SaleEntity position : positionList) {
			Row row = sheet.createRow(rowIdx++);
			createRowData(row, cellStyle, columns, position);
		}
		autoResize(sheet, columns.size());
	}

	private void createRowData(Row row, CellStyleXls cellStyle, List<SaleCommissionColumnsEnum> columns, SaleEntity position) {


		for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
			SaleCommissionColumnsEnum column = columns.get(columnIndex);

			switch (column) {


			case ID:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), position.getId().toString());
				break;

			case CAR_NAME:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), position.getCar().getName());
				break;

			case CAR_PLATE:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), position.getCar().getPlate());
				break;

			case CAR_PRICE:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), position.getCar().getPrice());
				break;

			case COMMISSION:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), position.getCommission());
				break;

			case SALLER_NAME:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), position.getSellerName());
				break;

			case CREATE_DATE:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), position.getCar().getName());
				break;

			default:
				createCell(row, columnIndex, cellStyle.getCellStyleCache().get(CellStyleEnum.DEFAULT), "-");
				break;
			}
		}

	}
}
