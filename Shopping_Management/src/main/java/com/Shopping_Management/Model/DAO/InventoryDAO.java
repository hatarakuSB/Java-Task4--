package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.LoginDTO;

@Repository
public class InventoryDAO {
	
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
	public ArrayList<LoginDTO> select() {
		// ステートメントはSQLを実行するオブジェクト
		java.sql.Statement stmt = null;
		// リザルトセットは結果を格納するオブジェクト
		ResultSet rs = null;

		String sql = "SELECT * FROM Shopping_Management_DB.m_product";
		ArrayList<LoginDTO> list = new ArrayList<LoginDTO>();

		try {
			// DB接続のメソッドを呼び出す
			connect();

			// ステートメントを作成
			stmt = con.createStatement();

			// SQLを実行し結果をリザルトセットへ格納
			rs = stmt.executeQuery(sql);

			// 結果を1行ずつループt
			while (rs.next()) {
				LoginDTO dto = new LoginDTO();
				dto.setID(rs.getInt("trade_id"));
				dto.setProductName(rs.getString("trade_name"));
				dto.setAmount(rs.getString("price"));
				dto.setPlace(rs.getString("place"));
				dto.setBuyDate(rs.getString("_date"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
