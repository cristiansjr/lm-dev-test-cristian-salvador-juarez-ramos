package com.lifebank.process;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifebank.pojo.RequestEncryptionDecryptionPojo;
import com.lifebank.pojo.ResponseEncryptionDecryptionPojo;

public class Encode64Process implements EncryptionDecryptionProcess {

	private Logger log;
	
	public Encode64Process() {
		this.log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public ResponseEncryptionDecryptionPojo process(RequestEncryptionDecryptionPojo request) {
		log.info("inicia proceso de encoding ENCODE64");
		ResponseEncryptionDecryptionPojo response = new ResponseEncryptionDecryptionPojo();
		String stringOriginal = request.getInputString();
		String stringEncode64 = new String(Base64.encodeBase64(stringOriginal.getBytes()));
		log.info("fin proceso de encoding ENCODE64");
		response.setOutputString(stringEncode64);
		
		return response;
	}

}
