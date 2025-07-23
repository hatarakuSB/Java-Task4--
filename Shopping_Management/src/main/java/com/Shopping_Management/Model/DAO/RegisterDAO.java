package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import parts.RegisterForm;

public class RegisterDAO {

    private Connection con = null;

    // DB接続
    public void connect() {
        try {
            con = Database.getConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 登録処理
    public void register(RegisterForm registerForm, int userId) {
        try {
            connect();

            String sql1 = "INSERT INTO M_PRODUCT (USER_ID,TRADE_NAME, DELETE_FLAG) VALUES (?, ?, 0)";
            PreparedStatement stmt1 = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            stmt1.setInt(1, userId);
            stmt1.setString(2, registerForm.getProductName());

            stmt1.executeUpdate();

            // PRODUCT_IDを取得
            ResultSet rs = stmt1.getGeneratedKeys();
            int productId = -1;
            if (rs.next()) {
                productId = rs.getInt(1);
            } else {
                throw new Exception("PRODUCT_ID の取得に失敗しました。");
            }

            String sql2 = "INSERT INTO PRODUCT_DETAIL (PRODUCT_ID, PRICE, PLACE, DATE, INVENTORY, DELETE_FLAG) VALUES (?, ?, ?, ?, ?, 0)";
            PreparedStatement stmt2 = con.prepareStatement(sql2);
            stmt2.setInt(1, productId);
            stmt2.setString(2, registerForm.getAmount());
            stmt2.setString(3, registerForm.getPlace());
            stmt2.setString(4, registerForm.getBuyDate());
            //stmt2.setInt(5, registerForm.getInventory());

            stmt2.executeUpdate();

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
