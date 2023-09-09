package com.example.demo.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.service.ServiceClass;

@Component
public class RunnerClass implements ApplicationRunner {
	@Autowired
	private ServiceClass serviceClass;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		serviceClass.initBeanObj("ZZZ", "Nusance", "Nobody is watching this file ever again");

	}

}
