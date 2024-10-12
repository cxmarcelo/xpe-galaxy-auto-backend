package br.com.mcb.galaxyauto.exceptions.handler;

import java.util.List;

import br.com.mcb.galaxyauto.dto.ErrorMessageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessageList {

	private Integer status;
	private String msg;
	private Long timeStamp;
	private List<ErrorMessageDto> errorList;

	private String code = ExceptionErrorCodeEnum.ERROR_LIST.getCode();

	public ErrorMessageList(Integer status, String msg, Long timeStamp, List<ErrorMessageDto> errorList) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
		this.errorList = errorList;
	}

}
