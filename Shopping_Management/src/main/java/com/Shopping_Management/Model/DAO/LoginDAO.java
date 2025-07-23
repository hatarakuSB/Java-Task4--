package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

public class LoginDAO {

	private Connection con = null;

	// DB接続
	public void connect() {
		try {
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ログイン認証し、成功したらユーザー情報（LoginDTO）を返す
	 * @param loginForm 入力されたユーザー名・パスワード
	 * @return ログイン成功時はLoginDTO、失敗時はnull
	 */
	public LoginDTO findByLoginForm(LoginForm loginForm) {
		String sql = "SELECT USER_ID, USER_NAME, PASS_WORD, AUTHORITY, DELETE_FLAG FROM M_USER WHERE USER_NAME = ? AND PASS_WORD = ?";
		LoginDTO dto = null;

		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, loginForm.getLoUser());
			stmt.setString(2, loginForm.getLoPass());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				// ログイン成功 → DTOに詰めて返す
				dto = new LoginDTO();
				dto.setUserId(rs.getInt("USER_ID"));
				dto.setUserName(rs.getString("USER_NAME"));
				dto.setPassword(rs.getString("PASS_WORD"));
				dto.setAuthority(rs.getBoolean("AUTHORITY"));
				dto.setDeleteFlag(rs.getBoolean("DELETE_FLAG"));
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

		return dto;
	}
}
