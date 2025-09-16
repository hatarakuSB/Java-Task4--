package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Repository;

import parts.RegisterForm;

@Repository
public class RegisterDAO {

	private Connection con = null;

	// DB接続
	public void connect() {
		try {
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 登録処理
	public boolean register(RegisterForm registerForm, int userId) {
	    boolean result = false;
	    try {
	        connect();

	        String sql = "INSERT INTO T_PRODUCT_DETAIL " +
	                     "(PRODUCT_ID, AMOUNT, PLACE, DATE, DELETE_FLAG, USER_ID) " +
	                     "VALUES (?, ?, ?, ?, 0, ?)";

	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setInt(1, registerForm.getProductId());
	        stmt.setString(2, registerForm.getAmount());
	        stmt.setString(3, registerForm.getPlace());
	        stmt.setString(4, registerForm.getBuyDate());
	        stmt.setInt(5, userId);

	        int rows = stmt.executeUpdate();
	        result = (rows > 0); 
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { con.close(); } catch (Exception e) { e.printStackTrace(); }
	    }
	    return result;
	}
}
