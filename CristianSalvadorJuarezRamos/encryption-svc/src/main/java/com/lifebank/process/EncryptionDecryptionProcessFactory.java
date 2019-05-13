package com.lifebank.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.lifebank.exception.LifebankEncryptionDecryptionException;

@Component
public class EncryptionDecryptionProcessFactory {
	private Environment env;
	private Logger log;

	public EncryptionDecryptionProcessFactory(Environment env) {
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	public EncryptionDecryptionProcess createEncryptionDecryptionProcess(String typeOfEncryptionDecryptionProcess) throws LifebankEncryptionDecryptionException {
		EncryptionDecryptionProcess encryptionDecryptionProcess;
		log.info("Se crea el proceso a utilizar");
		switch(typeOfEncryptionDecryptionProcess) {
		case "Encode64":
			encryptionDecryptionProcess = new Encode64Process();
			return encryptionDecryptionProcess;
		case "SHA512":
			encryptionDecryptionProcess = new SHA512Process(env);
			return encryptionDecryptionProcess;
		default:
			throw new LifebankEncryptionDecryptionException("createEncryptionDecryptionProcess type:" + typeOfEncryptionDecryptionProcess + " cannot be instantiated","createEncryptionDecryptionProcess","createEncryptionDecryptionProcess");
		}
	}
}
