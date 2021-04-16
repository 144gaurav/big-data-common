package com.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EmployeeDAO1;
import com.pojo.Employee;
//import com.repo.EmployeeDAO;

@RestController
public class EmployeeController {

	@Autowired
	@Qualifier(value="employeeDAO1Impl1")
	EmployeeDAO1 dao;

	@GetMapping("/employees-dummy")
	public List<Employee> allEmployees_dummy() {
		List<Employee> employees = new ArrayList<>();
		//employees.add(new Employee());
		employees.add(new Employee(12, "name", "address", 1234l));
		return employees;
	}

	@GetMapping("/employees-dummy/{empid}")
	public Employee empById_dummy(@PathVariable int empid) {
		Employee employee = new Employee(empid, "name", "address", 1234l);
		return employee;
	}

	@PostMapping("/employees-dummy")
	public Employee addEmployee(@RequestBody Employee employee) {
		employee.setEmpName(employee.getEmpName().toUpperCase());
		return employee;
	}

	@PostMapping("/employees")
	public Employee addEmployee_actual(@RequestBody Employee employee) {
		int added = dao.addEmployee(employee);
		if (added > 0) {
			employee.setEmpName(employee.getEmpName().toUpperCase());
			return employee;
		}
		return new Employee();
	}
	
	@GetMapping("/employees")
	public List<Employee> allEmployees() {
		return dao.findAll();
	}
	
//	@Autowired
//	EmployeeDAO repo;
//
//	@GetMapping("/employees-repo")
//	public List<Employee> allEmployees_repo() {
//		return repo.findAll();
//	}
//	@PostMapping("/employees-repo")
//	public ResponseEntity<Employee> employee_repo(@RequestBody Employee employee) {
//		Employee emp = repo.save(employee);
//		return new ResponseEntity<Employee>(emp,HttpStatus.CREATED);
//	}
//	@GetMapping("/employees-repo/{name}")
//	public ResponseEntity<List<Employee>> allEmployees_repo(@PathVariable String name) {
//		List<Employee> emp= repo.findAllByEmpName(name);
//		if(emp.size()!=0)
//			return new ResponseEntity<List<Employee>>(emp,HttpStatus.FOUND);
//		return new ResponseEntity(emp,HttpStatus.NOT_FOUND);
//	}

	
}