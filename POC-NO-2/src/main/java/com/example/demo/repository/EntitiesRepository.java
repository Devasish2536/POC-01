package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.EntitiesClass;

public interface EntitiesRepository extends JpaRepository<EntitiesClass, Integer> {

}
