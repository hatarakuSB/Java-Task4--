package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.HistoryDTO;

@Repository
public class HistoryDAO {

	private Connection con = null;

	public void connect() {
		try {
			con = Database.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ユーザーIDで履歴を取得
	public ArrayList<HistoryDTO> selectByUserId(int userId) {
	    ArrayList<HistoryDTO> list = new ArrayList<>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    String sql = """
	        SELECT 
	            p.TRADE_ID,
	            p.TRADE_NAME,
	            d.PRICE,
	            d.PLACE,
	            d.BUY_DATE
	        FROM 
	            M_PRODUCT p
	        INNER JOIN 
	            PRODUCT_DETAIL d ON p.PRODUCT_ID = d.PRODUCT_ID
	        WHERE 
	            p.USER_ID = ?
	        ORDER BY 
	            d.BUY_DATE DESC
	        """;

	    try {
	        connect();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            HistoryDTO dto = new HistoryDTO();
	            dto.setTradeId(rs.getInt("TRADE_ID"));
	            dto.setProductName(rs.getString("TRADE_NAME"));
	            dto.setPrice(rs.getString("PRICE"));
	            dto.setPlace(rs.getString("PLACE"));
	            dto.setBuyDate(rs.getString("BUY_DATE"));
	            list.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return list;
	}

	// 日付で履歴を絞り込む
	public ArrayList<HistoryDTO> selectByDateAndUserId(String date, int userId) {
	    ArrayList<HistoryDTO> list = new ArrayList<>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    String sql = """
	        SELECT 
	            p.TRADE_ID,
	            p.TRADE_NAME,
	            d.PRICE,
	            d.PLACE,
	            d.BUY_DATE
	        FROM 
	            M_PRODUCT p
	        INNER JOIN 
	            PRODUCT_DETAIL d ON p.PRODUCT_ID = d.PRODUCT_ID
	        WHERE 
	            DATE(d.BUY_DATE) = ?
	            AND p.USER_ID = ?
	            AND p.DELETE_FLAG = 0
	            AND d.DELETE_FLAG = 0
	        ORDER BY 
	            d.BUY_DATE DESC
	        """;

	    try {
	        connect();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, date);
	        pstmt.setInt(2, userId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            HistoryDTO dto = new HistoryDTO();
	            dto.setTradeId(rs.getInt("TRADE_ID"));
	            dto.setProductName(rs.getString("TRADE_NAME"));
	            dto.setPrice(rs.getString("PRICE"));
	            dto.setPlace(rs.getString("PLACE"));
	            dto.setBuyDate(rs.getString("BUY_DATE"));
	            list.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return list;
	}

}
