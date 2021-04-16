package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pojo.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Integer>{
	List<Employee>findAllByEmpName(String empName);

}
