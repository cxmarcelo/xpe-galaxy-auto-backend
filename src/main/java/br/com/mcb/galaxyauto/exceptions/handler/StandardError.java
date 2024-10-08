package br.com.mcb.galaxyauto.exceptions.handler;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer status;
	private String msg;
	private Long timeStamp;

}
