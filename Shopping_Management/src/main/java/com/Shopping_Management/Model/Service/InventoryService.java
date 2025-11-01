package com.Shopping_Management.Model.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.InventoryDAO;
import com.Shopping_Management.Model.DTO.InventoryDTO;

/**
 * 在庫サービスクラス
 */
@Service
public class InventoryService {

	private final InventoryDAO inventoryDAO;

	public InventoryService(InventoryDAO inventoryDAO) {
		this.inventoryDAO = inventoryDAO;
	}

	/**
	 * 在庫一覧を取得
	 *
	 * @param userId int
	 * @return List InventoryDTO
	 */
	public List<InventoryDTO> getInventoryList(int userId) {
		return inventoryDAO.selectDeletableList(userId);
	}

	/**
	 * 在庫削除（論理削除）を実行
	 *
	 * @param selectedIds List Integer
	 * @param userId int
	 * @return boolean 成功ならtrue、失敗ならfalse
	 */
	public boolean logicalDelete(List<Integer> selectedIds, int userId) {
		if (inventoryDAO.softDeleteDetailById(selectedIds, userId) == true) {
			return true;
		} else {
			return false;
		}

	}
}
