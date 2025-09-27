package com.Shopping_Management.Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Shopping_Management.Model.DTO.CategoryDTO;
import com.Shopping_Management.Model.DTO.ProductDTO;

/**
 * プルダウンDAOクラス
 */
@Repository
public class PullDownDAO {

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
	 * カテゴリ一覧を取得
	 *
	 * @return List CategoryDTO カテゴリリスト
	 */
	public List<CategoryDTO> findAllCategories() {
		List<CategoryDTO> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT CATEGORY_ID, CATEGORY_NAME " +
					"FROM M_CATEGORY " +
					"WHERE DELETE_FLAG = 0 " +
					"ORDER BY SORT_ORDER";

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategoryName(rs.getString("CATEGORY_NAME"));
				list.add(dto);
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
		return list;
	}

	/**
	 * 指定カテゴリの商品一覧を取得
	 *
	 * @param categoryId int
	 * @return List ProductDTO 商品リスト
	 */
	public List<ProductDTO> findProductsByCategory(int categoryId) {
		List<ProductDTO> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT PRODUCT_ID, PRODUCT_NAME " +
					"FROM M_PRODUCT " +
					"WHERE DELETE_FLAG = 0 " +
					"  AND CATEGORY_ID = ? " +
					"ORDER BY PRODUCT_NAME";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, categoryId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProductId(rs.getInt("PRODUCT_ID"));
				dto.setProductName(rs.getString("PRODUCT_NAME"));
				list.add(dto);
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
		return list;
	}
}
