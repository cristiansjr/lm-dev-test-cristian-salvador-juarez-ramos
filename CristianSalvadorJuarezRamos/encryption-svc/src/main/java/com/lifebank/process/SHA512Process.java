package com.lifebank.process;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.lifebank.pojo.RequestEncryptionDecryptionPojo;
import com.lifebank.pojo.ResponseEncryptionDecryptionPojo;

public class SHA512Process implements EncryptionDecryptionProcess {
	private Logger log;
	private Environment env;

	public SHA512Process(Environment env) {
		this.log = LoggerFactory.getLogger(getClass());
		this.env = env;
	}

	@Override
	public ResponseEncryptionDecryptionPojo process(RequestEncryptionDecryptionPojo request) {
		log.info("inicio del proceso SHA512Process");
		ResponseEncryptionDecryptionPojo response = new ResponseEncryptionDecryptionPojo();
		String generatedPassword = null;
		String passwordToHash = request.getInputString();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(env.getProperty("service.key.sha512").getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
	         StringBuilder sb = new StringBuilder();
	         for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	         }
	         generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		}
		log.info("fin del proceso SHA512Process");
		response.setOutputString(generatedPassword);
		return response;
	}

}
