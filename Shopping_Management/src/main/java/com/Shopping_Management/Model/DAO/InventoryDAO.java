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
	 * @return 削除フラグが立っていないデータのみ取得
	 */
	public ArrayList<LoginDTO> selectAll() {
		java.sql.Statement stmt = null;
		ResultSet rs = null;

		// 削除されていないデータのみ取得
		String sql = "SELECT * FROM Shopping_Management_DB.m_product WHERE DELETE_FLAG = 0";
		ArrayList<LoginDTO> list = new ArrayList<>();

		try {
			connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				LoginDTO dto = new LoginDTO();
				dto.setId(rs.getInt("PRODUCT_ID"));
			    dto.setProductName(rs.getString("TRADE_NAME"));
				dto.setBuyDate(rs.getString("_DATE"));
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

	/**
	 * 指定されたIDのレコードを仮削除する処理（DELETE_FLAG を 1 に更新）
	 */
	public void softDeleteById(int id) {
		java.sql.PreparedStatement pstmt = null;
		String sql = "UPDATE Shopping_Management_DB.m_product SET DELETE_FLAG = 1 WHERE PRODUCT_ID = ?";

		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
