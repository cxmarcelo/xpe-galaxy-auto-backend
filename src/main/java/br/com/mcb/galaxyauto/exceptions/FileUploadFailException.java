package br.com.mcb.galaxyauto.exceptions;

public class FileUploadFailException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FileUploadFailException(String msg) {
		super(msg);
	}

	public FileUploadFailException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
