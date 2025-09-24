package com.Shopping_Management.Controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DTO.InventoryDTO;
import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.Service.InventoryService;

import config.AppConstants;

@Controller
public class InventoryController {

	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
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
		
	    List<InventoryDTO> inventoryList = inventoryService.getInventoryList(loginUser.getUserId());
	    model.addAttribute("items", inventoryList);

		return "Inventory";
	}

	@PostMapping("/Inventory/Delete")
	public String deleteInventory(@RequestParam(required = false) List<Integer> selectedIds,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/Login";
	    }

	    boolean success = inventoryService.logicalDelete(selectedIds, loginUser.getUserId());
	    
	    if (success) {
	        redirectAttributes.addFlashAttribute("message", "選択した商品を削除しました。");
	        redirectAttributes.addFlashAttribute("messageClass", "message-box success-box");
	    } else {
	        redirectAttributes.addFlashAttribute("message", "削除処理でエラーが発生しました。");
	        redirectAttributes.addFlashAttribute("messageClass", "message-box error-box");
	    }
	    
		return "redirect:/Inventory";
	}

}
