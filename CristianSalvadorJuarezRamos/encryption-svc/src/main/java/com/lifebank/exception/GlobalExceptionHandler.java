package com.lifebank.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lifebank.pojo.error.ResponseError;

@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger log;
	private Environment env;
	
	@Autowired	
	public GlobalExceptionHandler(Environment env) {
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public ResponseError handleException(Exception ex){
		ResponseError responseError = new ResponseError();
		String messageError = "";
		log.info("Obteniendo Exception error");
		if(ex.getClass() == LifebankEncryptionDecryptionException.class) {
			LifebankEncryptionDecryptionException ledex = (LifebankEncryptionDecryptionException) ex;
			messageError = ledex.getErrorCode();
		} else {
			messageError = env.getProperty("service.configurations.error.general");
		}
		responseError.setErrorString(messageError);
		
		return responseError;
	}
}
