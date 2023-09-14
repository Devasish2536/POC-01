package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="Employee_tab")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id")
private Integer id;
	@Column(name="Name")
private String name;
	@Column(name="Salary")
private Float salary;
	@Column(name="Designation")
private String designation;
	@Column(name="Department")
private String department;
	@Column(name="Address")
private String address;
}
