package com.lifebank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifebank.exception.LifebankLoginGeneralException;
import com.lifebank.exception.LifebankMaxLoginAttException;
import com.lifebank.pojo.service.login.RequestLoginPojo;
import com.lifebank.pojo.service.login.ResponseLoginPojo;
import com.lifebank.process.ClientLoginProcess;

@RestController
@PropertySource("classpath:configuration.properties")
@RequestMapping
public class ClientLoginController {
	private Environment env;
	private Logger log;
	private ClientLoginProcess clientLoginProcess;
	
	public ClientLoginController(Environment env, ClientLoginProcess clientLoginProcess) {
		this.env = env;
		this.clientLoginProcess = clientLoginProcess;
		this.log = LoggerFactory.getLogger(getClass());
	}

	@PostMapping("${service.url.endpoint.login}")
	public ResponseLoginPojo clientLoginController(@RequestBody RequestLoginPojo request) throws LifebankMaxLoginAttException, LifebankLoginGeneralException {
		ResponseLoginPojo response = new ResponseLoginPojo();
		log.info("Inicio Login Controller");
		response = clientLoginProcess.process(request);
		log.info("Fin Login Controller");
		return response;
		
	}
}
