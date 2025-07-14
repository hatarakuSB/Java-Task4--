package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

public class LoginDAO {
	
	private Connection con = null;
	
	/**
	 * DBに接続する処理	
	 */
	public void connect() {
		try {
			// DBに接続
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * todolistテーブルからデータを取得する処理
	 * @return 全件取得した結果
	 */
	public boolean select(LoginForm loginForm) {
		LoginDTO dto = new LoginDTO();
		String sql = "select AUTHORITY from m_user where USER_NAME = ? and PASS_WORD = ?";
		try {
			// DB接続のメソッドを呼び出す
			connect();

			// ステートメントを作成
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1,loginForm.getLoUser());
			stmt.setString(2,loginForm.getLoPass());
			
			// SQLを実行し結果をリザルトセットへ格納
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				dto.setAuthority(rs.getBoolean("AUTHORITY"));
			}
			else {
				dto.setAuthority(false);
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
		return dto.isAuthority();
	}
}
