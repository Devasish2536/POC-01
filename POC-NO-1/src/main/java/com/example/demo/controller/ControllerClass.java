package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ServiceClass;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

@RestController
public class ControllerClass {
	@Autowired
	private ServiceClass service;

	@GetMapping("/startConversion")
	@ResponseStatus(code = HttpStatus.OK)
	public String startConversion() throws StreamWriteException, DatabindException, IOException {
		service.jsonConversion();
		return "Successfully converted into Json Format";
	}
}
