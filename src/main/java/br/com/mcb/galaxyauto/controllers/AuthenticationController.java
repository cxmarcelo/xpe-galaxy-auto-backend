package br.com.mcb.galaxyauto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcb.galaxyauto.dto.AuthenticationResponseDto;
import br.com.mcb.galaxyauto.dto.LoginRequestDto;
import br.com.mcb.galaxyauto.dto.NewPasswordRequestDto;
import br.com.mcb.galaxyauto.service.CognitoService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

	@Autowired
	private CognitoService cognitoService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
		String token = cognitoService.login(loginRequest.getUsername(), loginRequest.getPassword());
		return ResponseEntity.ok(new AuthenticationResponseDto(token));
	}

    @PostMapping("/respond-to-new-password")
    public ResponseEntity<AuthenticationResponseDto> respondToNewPassword(@RequestBody NewPasswordRequestDto request) {
        String token = cognitoService.respondToNewPasswordRequired(request.getSession(), request.getUsername(), request.getNewPassword());
        return ResponseEntity.ok(new AuthenticationResponseDto(token));
    }

}
