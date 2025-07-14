package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class Database {
	public static Connection getConnect() {
		Connection con = null;
		try {
			// DBに接続
			// con = DriverManager.getConnection(URL, USER, PASS);
			ResourceBundle rb = ResourceBundle.getBundle("application");
			con = DriverManager.getConnection(rb.getString("db"), rb.getString("user"), rb.getString("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
