package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pojo.Employee;

@Repository
public class EmployeeDAO1Impl1 implements EmployeeDAO1 {
	@Autowired
	JdbcTemplate template;
	@Override
	public int addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		int added = 0;
		String ADD_EMPLOYEE = "insert into employee values(?,?,?,?)";
		added=template.update(ADD_EMPLOYEE, employee.getEmpId(), employee.getEmpName(), employee.getSalary(),
				employee.getEmpAddress());
		return added;
	}
	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		String FIND_ALL="select * from employee";

		List<Employee> employees=template.query(FIND_ALL, new RowMapper<Employee>() {

			@Override
			public Employee mapRow(ResultSet set, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Employee employee=new Employee(set.getInt(1),
						set.getString(2),
						set.getString("empAddress"),
						set.getLong("salary"));
				return employee;
			}

		});
		return employees;
	}

}