package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.InventoryDTO;

@Repository
public class InventoryDAO {

	private Connection con = null;

	/**
	 * DB接続
	 */
	public void connect() {
		try {
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 表示用の在庫一覧を取得（カテゴリ名・最新日を含む）
	 */
	public ArrayList<InventoryDTO> selectDeletableList(int userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<InventoryDTO> list = new ArrayList<>();

		String sql = """
				SELECT
				    d.DETAIL_ID,
				    c.CATEGORY_NAME,
				    p.PRODUCT_NAME,
				    d.DATE
				FROM T_PRODUCT_DETAIL d
				INNER JOIN M_PRODUCT p ON d.PRODUCT_ID = p.PRODUCT_ID
				INNER JOIN M_CATEGORY c ON p.CATEGORY_ID = c.CATEGORY_ID
				WHERE d.USER_ID = ?
				  AND d.DELETE_FLAG = 0
				  AND p.DELETE_FLAG = 0
				ORDER BY d.DATE DESC
				        """;

		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				InventoryDTO dto = new InventoryDTO();
				dto.setDetailId(rs.getInt("DETAIL_ID"));
				dto.setCategoryName(rs.getString("CATEGORY_NAME"));
				dto.setProductName(rs.getString("PRODUCT_NAME"));
				dto.setLatestDate(rs.getString("DATE"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 指定されたユーザーの、指定された商品詳細を論理削除
	 */
	public void softDeleteDetailById(List<Integer> selectedIds, int userId) {
		String sql = """
				    UPDATE T_PRODUCT_DETAIL
				    SET DELETE_FLAG = 1
				    WHERE DETAIL_ID = ?
				      AND USER_ID = ?
				""";

		 try {
		        connect();
		        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
		            for (Integer id : selectedIds) {
		                pstmt.setInt(1, id);
		                pstmt.setInt(2, userId);
		                pstmt.executeUpdate();
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (con != null) con.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 }
}
