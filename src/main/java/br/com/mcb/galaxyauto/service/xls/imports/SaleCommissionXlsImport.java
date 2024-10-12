package br.com.mcb.galaxyauto.service.xls.imports;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.mcb.galaxyauto.dto.ErrorMessageDto;
import br.com.mcb.galaxyauto.entities.SaleEntity;
import br.com.mcb.galaxyauto.exceptions.DataIntegrityException;
import br.com.mcb.galaxyauto.exceptions.DataIntegrityListException;
import br.com.mcb.galaxyauto.exceptions.FileIOException;

@Component
public class SaleCommissionXlsImport {

	public List<SaleEntity> getSalesInFile(MultipartFile multipartFile) {
		List<SaleEntity> saleEntityList = null;

		try {
			saleEntityList = readSalesInFile(multipartFile);
		} catch (IOException e) {
			throw new FileIOException("Falha para ler o arquivo excel.");
		}

		return saleEntityList;
	}

	private List<SaleEntity> readSalesInFile(MultipartFile multipartFile) throws IOException {
		InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());
		Workbook workbook = new XSSFWorkbook(inputStream);

		Sheet firstSheet = workbook.getSheetAt(0);

		List<ErrorMessageDto> errorList = new ArrayList<ErrorMessageDto>();
		List<SaleEntity> saleEntityList = new ArrayList<SaleEntity>();

		int rowIndex = 0;
		rowLoop: while (true) {

			rowIndex++;
			Row row = firstSheet.getRow(rowIndex);
			if (row == null) {
				break rowLoop;
			}

			Cell cell = row.getCell(0);
			String value = null;
			try {
				if(cell != null) {
					value = cell.getStringCellValue(); 
				}
			} catch (Exception e) {
				value = Double.toString(cell.getNumericCellValue());
			}

			if (value == null || value.equals("") || value.equals("0")) {
				break rowLoop;
			}

			SaleEntity saleEntity = new SaleEntity();
			validateRow(row, saleEntity, errorList);
			saleEntityList.add(saleEntity);
		}

		workbook.close();

		if(errorList.size() > 0) {
			throw new DataIntegrityListException("Erro na leitura da planilha", errorList);
		}

		if(saleEntityList.size() == 0) {
			throw new DataIntegrityException("Nenhuma Liquidação foi encontrada na planilha.");
		}

		return saleEntityList;
	}

	private void validateRow(Row row, SaleEntity saleEntity, List<ErrorMessageDto> errorList) {
		for (int colNumber = 0; colNumber < row.getLastCellNum(); colNumber++) {
			Cell cell = row.getCell(colNumber);
			handleCell(row, colNumber, cell, saleEntity, errorList);
		}
	}

	private void handleCell(Row row, int colNumber, Cell cell, SaleEntity saleEntity, List<ErrorMessageDto> errorList) {
		if (colNumber == 0) {
			idValidate(row, cell, saleEntity, errorList);
		} else if (colNumber == 1) {
			commissionValueValidate(row, cell, saleEntity, errorList);
		}
	}

	private void idValidate(Row row, Cell cell, SaleEntity saleEntity, List<ErrorMessageDto> errorList) {
		String cellValue = getStringCellValue(cell);
		if(cellValue == null) {
			ErrorMessageDto error = new ErrorMessageDto(row.getRowNum()+1, "Id", "Id não preenchido.");
			errorList.add(error);
			return;
		}

		try {
			UUID uuid = UUID.fromString(cellValue);
			saleEntity.setId(uuid);
		} catch (Exception e) {
			ErrorMessageDto error = new ErrorMessageDto(row.getRowNum()+1, "Id", "Id não preenchidao ou inválido.");
			errorList.add(error);
		}
	}


	private void commissionValueValidate(Row row, Cell cell, SaleEntity saleEntity, List<ErrorMessageDto> errorList) {
		String cellValue = getStringCellValue(cell);
		if(cellValue == null) {
			ErrorMessageDto error = new ErrorMessageDto(row.getRowNum()+1, "Comissão", "Valor da comissão não preenchido.");
			errorList.add(error);
			return;
		}

		try {
			BigDecimal commissionValue = new BigDecimal(cellValue);

			if(commissionValue.compareTo(BigDecimal.ZERO) <= 0 ) {
				ErrorMessageDto error = new ErrorMessageDto(row.getRowNum()+1, "Comissão", "Valor da comissão não pode ser menor que 0.");
				errorList.add(error);
			}

			saleEntity.setCommission(commissionValue);
		} catch (Exception e) {
			ErrorMessageDto error = new ErrorMessageDto(row.getRowNum()+1, "Comissão", "Valor da comissão é inválido. Valor: " + cellValue);
			errorList.add(error);
		}
	}


	private String getStringCellValue(Cell cell) {
		if(cell == null) return null;

		if(cell.getCellType().equals(CellType.STRING)) {
			return cell.getStringCellValue();
		} else if(cell.getCellType().equals(CellType.NUMERIC)) {
			Integer value = (int) cell.getNumericCellValue();
			return String.valueOf(value);
		} else if(cell.getCellType().equals(CellType.BOOLEAN)){
			Boolean value = cell.getBooleanCellValue();
			return String.valueOf(value);
		}

		return null;
	}

}
