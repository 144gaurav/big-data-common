package com.dao;

import java.util.List;

import com.pojo.Employee;

public interface EmployeeDAO1 {

	int addEmployee(Employee employee);
	List<Employee> findAll();
}
