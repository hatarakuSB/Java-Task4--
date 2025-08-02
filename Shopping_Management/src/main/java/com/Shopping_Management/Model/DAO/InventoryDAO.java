package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.InventoryDetailDTO;

@Repository
public class InventoryDAO {

	private Connection con = null;

	/**
	 * DBに接続する処理	
	 */
	public void connect() {
		try {
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 表示用の在庫一覧を取得（在庫数・最新日を含む）
	 */
	public ArrayList<InventoryDetailDTO> selectDeletableList(int userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<InventoryDetailDTO> list = new ArrayList<>();

		String sql = """
			SELECT
			    p.PRODUCT_ID,
			    p.TRADE_NAME,
			    COUNT(d.PRODUCT_ID) AS stock_count,
			    MAX(d.BUY_DATE) AS latest_date
			FROM
			    M_PRODUCT p
			INNER JOIN
			    PRODUCT_DETAIL d ON p.PRODUCT_ID = d.PRODUCT_ID
			WHERE
			    p.DELETE_FLAG = 0
			    AND d.DELETE_FLAG = 0
			    AND d.USER_ID = ?
			GROUP BY
			    p.PRODUCT_ID, p.TRADE_NAME
			ORDER BY
			    latest_date DESC
		""";

		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				InventoryDetailDTO dto = new InventoryDetailDTO();
				dto.setProductId(rs.getInt("PRODUCT_ID"));
				dto.setProductName(rs.getString("TRADE_NAME"));
				dto.setStockCount(rs.getInt("stock_count"));
				dto.setLatestDate(rs.getString("latest_date"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 指定されたユーザーの、指定された商品IDに該当する在庫を論理削除
	 */
	public void softDeleteDetailById(int productDetailId, int userId) {
	    PreparedStatement pstmt = null;
	    String sql = "UPDATE PRODUCT_DETAIL SET DELETE_FLAG = 1 WHERE PRODUCT_ID = ? AND USER_ID = ?";

	    try {
	        connect();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productDetailId);
	        pstmt.setInt(2, userId);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}