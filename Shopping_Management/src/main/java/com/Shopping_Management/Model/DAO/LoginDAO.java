package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

/**
 * ログインDAOクラス
 */
@Repository
public class LoginDAO {

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
     * ログイン情報を検索
     *
     * @param loginForm LoginForm
     * @return LoginDTO
     */
    public LoginDTO findByLoginForm(LoginForm loginForm) {
        String sql = "SELECT USER_ID, USER_NAME, PASS_WORD, AUTHORITY, DELETE_FLAG "
                   + "FROM M_USER WHERE USER_NAME = ? AND PASS_WORD = ?";
        LoginDTO dto = null;

        try {
            connect();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, loginForm.getLoUser());
            stmt.setString(2, loginForm.getLoPass());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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
