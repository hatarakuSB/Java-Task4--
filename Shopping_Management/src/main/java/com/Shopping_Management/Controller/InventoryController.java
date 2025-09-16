package com.Shopping_Management.Controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Shopping_Management.Model.DAO.InventoryDAO;
import com.Shopping_Management.Model.DTO.InventoryDTO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import config.AppConstants;

@Controller
public class InventoryController {

	private final InventoryDAO inventoryDAO;

	public InventoryController(InventoryDAO inventoryDAO) {
		this.inventoryDAO = inventoryDAO;
	}

	// 在庫一覧画面の表示（集計済みデータ）
	@GetMapping("/Inventory")
	public String showInventory(
			Model model,
			HttpSession session) {

		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/Login";
		}
		model.addAttribute("loginUser", loginUser);

		// ページ名
		model.addAttribute("pageTitle", AppConstants.TITLE_INVENTORY);
		
		int userId = loginUser.getUserId();

		ArrayList<InventoryDTO> allItems = inventoryDAO.selectDeletableList(userId);
		
		model.addAttribute("items", allItems);

		return "Inventory";
	}

	@PostMapping("/Inventory/Delete")
	public String deleteInventory(@RequestParam(required = false) List<Integer> selectedIds,
			HttpSession session) {

		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/Login";
	    }

	    int userId = loginUser.getUserId();

	    if (selectedIds != null) {
	        for (Integer id : selectedIds) {
	            inventoryDAO.softDeleteDetailById(id, userId);
	        }
	    }
		return "redirect:/Inventory";
	}

}
