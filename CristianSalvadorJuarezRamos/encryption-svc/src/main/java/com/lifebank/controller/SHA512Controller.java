package com.lifebank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifebank.exception.LifebankEncryptionDecryptionException;
import com.lifebank.pojo.RequestEncryptionDecryptionPojo;
import com.lifebank.pojo.ResponseEncryptionDecryptionPojo;
import com.lifebank.process.EncryptionDecryptionProcess;
import com.lifebank.process.EncryptionDecryptionProcessFactory;

@RestController
@PropertySource("classpath:configuration.properties")
@RequestMapping
public class SHA512Controller {
	private Environment env;
	private Logger log;
	private EncryptionDecryptionProcessFactory encryptionDecryptionProcessFactory;
	
	public SHA512Controller(Environment env, EncryptionDecryptionProcessFactory encryptionDecryptionProcessFactory) {
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());
		this.encryptionDecryptionProcessFactory = encryptionDecryptionProcessFactory;
	}
	
	@PostMapping("${service.url.endpoint.sha512}")
	public ResponseEncryptionDecryptionPojo sha512Controller(@RequestBody RequestEncryptionDecryptionPojo request) throws LifebankEncryptionDecryptionException {
		ResponseEncryptionDecryptionPojo response = new ResponseEncryptionDecryptionPojo();
		log.info("Inicia el proceso del controller");
		try {
			EncryptionDecryptionProcess encryptionDecryptionProcess = encryptionDecryptionProcessFactory.createEncryptionDecryptionProcess("SHA512");
			response = encryptionDecryptionProcess.process(request);
			log.info("Fin el proceso del controller");
			return response;
		} catch (LifebankEncryptionDecryptionException e) {
			log.error("encodeBase64 - Error Message: {}, en la línea {}, en el método {}", e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
			if ("".equals(e.getErrorCode())) {
				throw new LifebankEncryptionDecryptionException(env.getProperty("service.configurations.error.general"),
						env.getProperty("service.configurations.process.default"),
						env.getProperty("service.configurations.method.default"));
			} else {
				throw e;
			}
		}
	}

}
