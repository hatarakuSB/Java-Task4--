package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.HistoryDTO;

import parts.HistoryForm;

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
	
	//検索
	public List<HistoryDTO> searchByConditions(HistoryForm form, int userId) {
        List<HistoryDTO> list = new ArrayList<>();

        try {
            connect();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    c.CATEGORY_NAME, ");
            sql.append("    p.PRODUCT_NAME, ");
            sql.append("    d.AMOUNT, ");
            sql.append("    d.PLACE, ");
            sql.append("    d.DATE ");
            sql.append("FROM T_PRODUCT_DETAIL d ");
            sql.append("JOIN M_PRODUCT p ON d.PRODUCT_ID = p.PRODUCT_ID ");
            sql.append("JOIN M_CATEGORY c ON p.CATEGORY_ID = c.CATEGORY_ID ");
            sql.append("WHERE d.USER_ID = ? ");
            sql.append("  AND p.DELETE_FLAG = 0 ");

            // 条件パラメータを動的に追加
            ArrayList<Object> params = new ArrayList<>();

            params.add(userId);

            if (form.getProductId() != null) {
                sql.append(" AND p.PRODUCT_ID = ? ");
                params.add(form.getProductId());
            }

            if (form.getPlace() != null && !form.getPlace().isEmpty()) {
                sql.append(" AND d.PLACE = ? ");
                params.add(form.getPlace());
            }

            if (form.getMoneyFrom() != null) {
                sql.append(" AND d.AMOUNT >= ? ");
                params.add(form.getMoneyFrom());
            }

            if (form.getMoneyTo() != null) {
                sql.append(" AND d.AMOUNT <= ? ");
                params.add(form.getMoneyTo());
            }

            if (form.getDateFrom() != null && !form.getDateFrom().isEmpty()) {
                sql.append(" AND d.DATE >= ? ");
                params.add(form.getDateFrom() + " 00:00:00");
            }

            if (form.getDateTo() != null && !form.getDateTo().isEmpty()) {
                sql.append(" AND d.DATE <= ? ");
                params.add(form.getDateTo() + " 23:59:59");
            }

            sql.append(" ORDER BY d.DATE DESC ");

            PreparedStatement ps = con.prepareStatement(sql.toString());

            // プレースホルダに値を順番に設定
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HistoryDTO dto = new HistoryDTO();
                dto.setCategoryName(rs.getString("CATEGORY_NAME"));
                dto.setProductName(rs.getString("PRODUCT_NAME"));
                dto.setAmount(rs.getInt("AMOUNT"));
                dto.setPlace(rs.getString("PLACE"));
                dto.setBuyDate(rs.getString("DATE"));
                list.add(dto);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
