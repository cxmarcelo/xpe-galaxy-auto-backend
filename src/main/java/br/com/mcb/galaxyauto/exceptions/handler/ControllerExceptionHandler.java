package br.com.mcb.galaxyauto.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.mcb.galaxyauto.exceptions.DataIntegrityException;
import br.com.mcb.galaxyauto.exceptions.DataIntegrityListException;
import br.com.mcb.galaxyauto.exceptions.FileUploadFailException;
import br.com.mcb.galaxyauto.exceptions.NewPasswordRequiredException;
import br.com.mcb.galaxyauto.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(FileUploadFailException.class)
	public ResponseEntity<StandardError> FileUploadFail(FileUploadFailException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FormsError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FormFieldError> dto = new ArrayList<FormFieldError>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldErro -> {
            String message = messageSource.getMessage(fieldErro, LocaleContextHolder.getLocale());
            FormFieldError error = new FormFieldError(fieldErro.getField(), message);
            dto.add(error);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FormsError(HttpStatus.BAD_REQUEST.value(), "Invalid Fields.", System.currentTimeMillis(), dto));
    }
	
	@ExceptionHandler(DataIntegrityListException.class)
    public ResponseEntity<ErrorMessageList> dataIntegrityListException(DataIntegrityListException e, HttpServletRequest request){
		ErrorMessageList err = new ErrorMessageList(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis(), e.getErrorList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
	
	@ExceptionHandler(NewPasswordRequiredException.class)
	public ResponseEntity<LoginError> newPasswordRequiredException(NewPasswordRequiredException e, HttpServletRequest request){
		LoginError err = new LoginError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis(), LoginErrorCodeEnum.NEW_PASSWORD_REQUIRED, e.getSession());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<LoginError> loginException(LoginException e, HttpServletRequest request){
		LoginError err = new LoginError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis(), LoginErrorCodeEnum.LOGIN_FAIL);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
}
