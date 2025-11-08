package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Repository;

/**
 * ログインログDAOクラス
 */
@Repository
public class LoginLogDAO {

	private Connection con = null;

	/**
	 * DB接続
	 */
	private void connect() {
		try {
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ログを登録
	 *
	 * @param userId Integer ユーザーID（失敗時は null）
	 * @param loginUserName String ユーザー名または入力値
	 * @param eventTypeCode String イベント種別コード (01=成功, 02=失敗, 03=ログアウト)
	 */
	public void insertLog(Integer userId, String loginUserName, String eventTypeCode) {
		String sql = "INSERT INTO T_LOGIN_LOG "
				+ "(USER_ID, LOGIN_USER_NAME, LOG_EVENT_TYPE_CODE, LOG_EVENT_TIME) "
				+ "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);

			if (userId != null) {
				stmt.setInt(1, userId);
			} else {
				stmt.setNull(1, java.sql.Types.INTEGER);
			}

			stmt.setString(2, loginUserName);
			stmt.setString(3, eventTypeCode);

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
