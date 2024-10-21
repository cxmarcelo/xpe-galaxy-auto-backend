package br.com.mcb.galaxyauto.exceptions;

public class LoginFailException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public LoginFailException(String msg) {
		super(msg);
	}

	public LoginFailException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
