package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.UserListDTO;

/**
 * ユーザー一覧DAOクラス
 */
@Repository
public class UserListDAO {

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
	 * ユーザー全件取得
	 *
	 * @return List<UserListDTO> 有効なユーザーリスト
	 */
	public List<UserListDTO> findAll() {
		List<UserListDTO> list = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT USER_ID, USER_NAME, PASS_WORD FROM M_USER WHERE DELETE_FLAG = 0";

		try {
			connect();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				UserListDTO dto = new UserListDTO();
				dto.setUserId(rs.getInt("USER_ID"));
				dto.setUserName(rs.getString("USER_NAME"));
				dto.setPassword(rs.getString("PASS_WORD"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * ユーザーと関連する商品詳細を削除（トランザクション）
	 *
	 * @param userId int 削除対象ユーザーID
	 */
	public void deleteUserWithDetails(int userId) {
		PreparedStatement stmtDetail = null;
		PreparedStatement stmtUser = null;

		String sqlDetail = "DELETE FROM T_PRODUCT_DETAIL WHERE USER_ID = ?";
		String sqlUser = "DELETE FROM M_USER WHERE USER_ID = ?";

		try {
			connect();
			con.setAutoCommit(false); // トランザクション開始

			// 商品詳細を削除
			stmtDetail = con.prepareStatement(sqlDetail);
			stmtDetail.setInt(1, userId);
			stmtDetail.executeUpdate();

			// ユーザーを削除
			stmtUser = con.prepareStatement(sqlUser);
			stmtUser.setInt(1, userId);
			stmtUser.executeUpdate();

			// コミット
			con.commit();

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback();
			} catch (Exception rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();

		} finally {
			try {
				if (stmtDetail != null)
					stmtDetail.close();
				if (stmtUser != null)
					stmtUser.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * CSVインポート
	 *
	 * @param dto UserListDTO インポート対象ユーザー
	 */
	public void insert(UserListDTO dto) {
		String sql = "INSERT IGNORE INTO M_USER (USER_ID, USER_NAME, PASS_WORD) VALUES (?, ?, ?)";

		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, dto.getUserId());
			stmt.setString(2, dto.getUserName());
			stmt.setString(3, dto.getPassword());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
