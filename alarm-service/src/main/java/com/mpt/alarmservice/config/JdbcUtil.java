package com.mpt.alarmservice.config;

import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class JdbcUtil {

    private final JdbcConfigValue jdbcConfigvalue;

    public JdbcUtil(JdbcConfigValue jdbcConfigValue) {
        this.jdbcConfigvalue = jdbcConfigValue;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(jdbcConfigvalue.getDbDriverName());
            conn = DriverManager.getConnection(jdbcConfigvalue.getJdbcUrl(), jdbcConfigvalue.getDbUsername(), jdbcConfigvalue.getDbPassword());
            if (conn != null) {
                System.out.println("DB Connection Success");
            }
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver Load Error");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB Connection Failed");
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs = null;
        }
        try {
            if (stmt != null) stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt = null;
        }
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
    }
}
