package br.com.mcb.galaxyauto.exceptions;

import java.util.List;

import br.com.mcb.galaxyauto.dto.ErrorMessageDto;

public class DataIntegrityListException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private List<ErrorMessageDto> errorList;

	public DataIntegrityListException(String msg, List<ErrorMessageDto> errorList) {
		super(msg);
		this.setErrorList(errorList);
	}

	public List<ErrorMessageDto> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorMessageDto> errorList) {
		this.errorList = errorList;
	}

}
