package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.UsersInfo;
import com.example.demo.repository.MyRepository;

@RestController
public class ProjectController {
	@Autowired
	private MyRepository repository;

	@GetMapping("/secured")
	public String showPage() {
		return "This is secured page";
	}

	@GetMapping("/public")
	public String showPage2() {
		return "This page for public use";
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UsersInfo userInfoObj) {
		UsersInfo saveObj = repository.save(userInfoObj);
		if (null != saveObj)
			return new ResponseEntity<>("User Register Successfull", HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Something Went Wrong Registration was Unsuccessfull",
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
