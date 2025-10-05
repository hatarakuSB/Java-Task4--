package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

/**
 * パスワード変更DAOクラス
 */
@Repository
public class PasswordChangeDAO {

    private Connection con = null;

    /**
     * DB接続
     */
    private void connect() throws Exception {
        con = Database.getConnect();
    }

    /**
     * ユーザーIDから現在のパスワードを取得
     *
     * @param userId int
     * @return String 現在のパスワード（存在しない場合は null）
     */
    public String getPasswordByUserId(int userId) {
        String sql = "SELECT PASS_WORD FROM M_USER WHERE USER_ID = ? AND DELETE_FLAG = 0";
        try {
            connect();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("PASS_WORD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * パスワード更新処理
     *
     * @param userId int
     * @param newPassword String
     * @return int 更新件数
     */
    public int updatePassword(int userId, String newPassword) {
        String sql = "UPDATE M_USER SET PASS_WORD = ? WHERE USER_ID = ? AND DELETE_FLAG = 0";
        try {
            connect();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
