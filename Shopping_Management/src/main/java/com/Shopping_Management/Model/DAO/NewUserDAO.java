package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import parts.NewUserForm;

@Repository 
public class NewUserDAO {
	private Connection con = null;

	public void connect() {
		try {
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ユーザー名の重複チェック
	public boolean existsByUsername(String username) {
		String sql = "SELECT COUNT(*) FROM m_user WHERE user_name = ?";
		boolean exists = false;

		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				exists = rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return exists;
	}

	// 新規ユーザー登録
	public void newUserInsert(NewUserForm user) {
		String sql = "INSERT INTO m_user(user_name, pass_word) VALUES (?, ?)";

		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());

			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
