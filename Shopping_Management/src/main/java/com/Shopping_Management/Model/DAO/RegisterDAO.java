package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import parts.RegisterForm;

public class RegisterDAO {
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
	public void register(RegisterForm registerForm) {
		String sql = "INSERT INTO m_product(trade_name, price, place, _date) VALUES( ?, ?, ?, ?)";
		try {
			// DB接続のメソッドを呼び出す
			connect();

			// ステートメントを作成
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, registerForm.getProductName());
			stmt.setString(2, registerForm.getAmount());
			stmt.setString(3, registerForm.getPlace());
			stmt.setString(4, registerForm.getBuyDate());
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
