package com.yll.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
	public static Connection conn;
	public static String driver;
	public static String url;
	public static String username;
	public static String password;

	public static PreparedStatement ps = null;
	public static ResultSet resultSet = null;

	static {
		try {
			InputStream in = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
			Properties properties = new Properties();
			properties.load(in);
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			Class.forName(driver);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void generateConn() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(StringBuffer sql, Object[] params) throws SQLException {
		ps = BaseDao.conn.prepareStatement(sql.toString());
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
		return ps.executeQuery();
	}

	public static int executeUpdate(StringBuffer sql, Object[] params) throws SQLException {
		ps = conn.prepareStatement(sql.toString());
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
		return ps.executeUpdate();
	}

	public static void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void closeConn() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}