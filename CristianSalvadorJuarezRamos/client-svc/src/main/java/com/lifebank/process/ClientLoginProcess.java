package com.lifebank.process;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.lifebank.exception.LifebankLoginGeneralException;
import com.lifebank.exception.LifebankMaxLoginAttException;
import com.lifebank.pojo.encryption.RequestEncryptionDecryptionPojo;
import com.lifebank.pojo.encryption.ResponseEncryptionDecryptionPojo;
import com.lifebank.pojo.esb.login.PostvalidateCredential;
import com.lifebank.pojo.esb.login.RequestESBLoginPojo;
import com.lifebank.pojo.esb.login.ResponseESBLoginPojo;
import com.lifebank.pojo.service.login.RequestLoginPojo;
import com.lifebank.pojo.service.login.ResponseLoginPojo;
import com.lifebank.utils.JWT;
import com.lifebank.utils.RestClient;

@Service
public class ClientLoginProcess {
	private Environment env;
	private RestClient restClient;
	private Logger log;
	
	public ClientLoginProcess(Environment env, RestClient restClient) {
		this.env = env;
		this.restClient = restClient;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	public ResponseLoginPojo process(RequestLoginPojo request) throws LifebankMaxLoginAttException, LifebankLoginGeneralException {
		Map<String, Object> mapJWT = new HashMap<String, Object>();
		String passSHA512 = "";
		String passBase64 = "";
		ResponseEncryptionDecryptionPojo responseEncDec = null;
		ResponseESBLoginPojo responseESBLoginPojo = null;
		ResponseLoginPojo response = new ResponseLoginPojo();
		
		responseEncDec = encode64(request.getUser());
		passBase64 = responseEncDec.getOutputString();
		responseEncDec = encode64(request.getPassword());
		passBase64 += responseEncDec.getOutputString();
		responseEncDec = sha512(passBase64);
		passSHA512 = responseEncDec.getOutputString();
		responseESBLoginPojo = esbClientLogin(request.getUser(),passSHA512, request.getIp());
		if (responseESBLoginPojo.getResponse().getLoginResponseCode().equals(0)) {
			mapJWT.put("userid", request.getUser());
			mapJWT.put("userip", request.getIp());
			
			JWT jwt = new JWT(mapJWT,
					env.getProperty("service.jwt.secret"),
					env.getProperty("service.jwt.subject"),
					env.getProperty("service.jwt.issuer"),
					Long.parseLong(env.getProperty("service.jwt.expiration-time")));
			
			response.setTkn(jwt.generate());
			return response;
		} else {
			if (responseESBLoginPojo.getResponse().getLoginResponseCode().equals(4)) {
				throw new LifebankMaxLoginAttException("Maximum number of attempts reached", "ClientLoginProcess", "process");
			} else {
				throw new LifebankLoginGeneralException("Login Failed", "ClientLoginProcess", "process");
			}
		}
	}
	
	private ResponseEncryptionDecryptionPojo encode64(String stringToEncode) {
		RequestEncryptionDecryptionPojo requestEncryptionDecryption = new RequestEncryptionDecryptionPojo();
		ResponseEncryptionDecryptionPojo response = new ResponseEncryptionDecryptionPojo();
		log.info("Inicio del Encode64");
		try {
			requestEncryptionDecryption.setInputString(stringToEncode);
			response = restClient.call(env.getProperty("url.service.encryption.encode64"), 
										HttpMethod.POST, 
										requestEncryptionDecryption, 
										new ParameterizedTypeReference<ResponseEncryptionDecryptionPojo>() {});
		} catch (NumberFormatException | InterruptedException | ExecutionException | TimeoutException e) {
			log.error(e.getMessage());
		}
		log.info("Fin del Encode64");
		return response;
	}
	
	private ResponseEncryptionDecryptionPojo sha512(String stringToSha512) {
		RequestEncryptionDecryptionPojo requestEncryptionDecryption = new RequestEncryptionDecryptionPojo();
		ResponseEncryptionDecryptionPojo response = new ResponseEncryptionDecryptionPojo();
		log.info("Inicio de la generacion del sha512");
		try {
			requestEncryptionDecryption.setInputString(stringToSha512);
			response = restClient.call(env.getProperty("url.service.encryption.sha512"), 
										HttpMethod.POST, 
										requestEncryptionDecryption, 
										new ParameterizedTypeReference<ResponseEncryptionDecryptionPojo>() {});
		} catch (NumberFormatException | InterruptedException | ExecutionException | TimeoutException e) {
			log.error(e.getMessage());
		}
		log.info("Fin de la generacion del sha512");
		return response;
	}
	
	private ResponseESBLoginPojo esbClientLogin(String user, String password, String ip) {
		RequestESBLoginPojo requestESBLogin = new RequestESBLoginPojo();
		ResponseESBLoginPojo responseESBLogin = new ResponseESBLoginPojo();
		PostvalidateCredential postvalidateCredential = new PostvalidateCredential();
		log.info("Inicio de login");
		try {
			postvalidateCredential.setUser(user);
			postvalidateCredential.setPassword(password);
			postvalidateCredential.setIp(ip);
			requestESBLogin.setPostvalidateCredential(postvalidateCredential);
			responseESBLogin = restClient.call(env.getProperty("url.service.esb.login"), 
										HttpMethod.POST, 
										requestESBLogin, 
										new ParameterizedTypeReference<ResponseESBLoginPojo>() {});
		} catch (NumberFormatException | InterruptedException | ExecutionException | TimeoutException e) {
			log.error(e.getMessage());
		}
		log.info("Fin de login");
		return responseESBLogin;
	}
}
