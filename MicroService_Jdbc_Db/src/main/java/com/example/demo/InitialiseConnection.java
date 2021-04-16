package com.example.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class InitialiseConnection {
    public static final HikariConfig HIKARI_CONFIG;

    static{
        HIKARI_CONFIG = new HikariConfig();
        HIKARI_CONFIG.setDriverClassName("com.mysql.cj.jdbc.Driver");
        HIKARI_CONFIG.setJdbcUrl("jdbc:mysql://localhost:3306/mysql");
        HIKARI_CONFIG.setUsername("root");
        HIKARI_CONFIG.setPassword("welcome");


    }

    public static void insertIntoTable(){
        String ADD_EMPLOYEE = "insert into employee values(?,?,?,?)";
        Connection connection = null;
        try {
            System.out.println(HIKARI_CONFIG.getJdbcUrl());
            DataSource datasource = new HikariDataSource(HIKARI_CONFIG);
            connection = datasource.getConnection();

            PreparedStatement ps = connection.prepareStatement(ADD_EMPLOYEE);
            ps.setInt(1, 111);
            ps.setString(2, "vishal");
            ps.setLong(3, 12000l);
            ps.setString(4, "alwar");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
