package com.pojo;

//import javax.persistence.Entity;
//import javax.persistence.Id;

//@Entity(name="SG_emp")
public class Employee {
//@Id
	int empId;
	String empName;
	String empAddress;
	long salary;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpAddress() {
		return empAddress;
	}
	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}
	public Employee(int empId, String empName, String empAddress, long salary) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empAddress = empAddress;
		this.salary = salary;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	

}
