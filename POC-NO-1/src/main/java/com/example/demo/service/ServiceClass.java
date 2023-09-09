package com.example.demo.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.JavaBean;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceClass {

	private JavaBean beanObj;

	public ServiceClass() {
		beanObj = new JavaBean();
	}

	@Autowired
	private ObjectMapper objectMapper;

	public void initBeanObj(String name, String title, String description) {
		beanObj.setName(name);
		beanObj.setTitle(title);
		beanObj.setDescription(description);
	}

	public void jsonConversion() throws StreamWriteException, DatabindException, IOException {
		File file = new File("src/main/resources/jsonfile");
		String json = objectMapper.writeValueAsString(beanObj);
		try (FileWriter writer = new FileWriter(file)) {
			writer.append(json);
		}
	}

}
