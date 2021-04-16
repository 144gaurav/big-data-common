package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pojo.Employee;

@Repository
public class EmployeeDAO1Impl implements EmployeeDAO1 {


	@Autowired
	DataSource dataSource;

	@Override
	public int addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		int added = 0;
		String ADD_EMPLOYEE = "insert into employee values(?,?,?,?)";

		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(ADD_EMPLOYEE);
			ps.setInt(1, employee.getEmpId());
			ps.setString(2, employee.getEmpName());
			ps.setLong(3, employee.getSalary());
			ps.setString(4, employee.getEmpAddress());
			added = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return added;
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}