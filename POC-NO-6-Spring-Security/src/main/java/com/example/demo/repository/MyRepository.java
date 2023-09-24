package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.UsersInfo;

public interface MyRepository extends JpaRepository<UsersInfo, Integer> {

	UsersInfo findByUsername(String username);

}
