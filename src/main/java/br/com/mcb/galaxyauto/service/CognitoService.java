package br.com.mcb.galaxyauto.service;

public interface CognitoService {

	String login(String username, String password);

	String respondToNewPasswordRequired(String session, String username, String newPassword);

}
