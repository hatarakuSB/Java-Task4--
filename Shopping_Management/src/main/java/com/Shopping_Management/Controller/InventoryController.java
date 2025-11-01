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

/**
 * 在庫コントローラークラス
 */
@Controller
public class InventoryController {

	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	/**
	 * 在庫一覧画面を表示
	 * 
	 * @param model Model
	 * @param session HttpSession
	 * @return String 遷移先ビュー名
	 */
	@GetMapping(AppConstants.INVENTORY_URL)
	public String showInventory(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		// ログイン情報をセッションから取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		// ログイン情報取得に失敗時、ログイン画面に戻る
		if (loginUser == null) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_SYSTEM_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS,AppConstants.MESSAGE_BOX_SYSTEM_ERROR);
			return AppConstants.REDIRECT_LOGIN;
		}
		model.addAttribute(AppConstants.ATTR_LOGIN_USER, loginUser);

		// 権限を設定
		model.addAttribute(AppConstants.ATTR_AUTHORITY, loginUser.isAuthority());

		// ページ名
		model.addAttribute(AppConstants.ATTR_PAGE_TITLE, AppConstants.TITLE_INVENTORY);

		// 在庫一覧を取得し、画面に渡す
		List<InventoryDTO> inventoryList = inventoryService.getInventoryList(loginUser.getUserId());
		// 在庫一覧取得に失敗時、ログイン画面に戻る
		if (inventoryList == null) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_SYSTEM_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS,AppConstants.MESSAGE_BOX_SYSTEM_ERROR);
			session.invalidate();
			return AppConstants.REDIRECT_LOGIN;
		}
		model.addAttribute(AppConstants.ATTR_ITEMS, inventoryList);

		return AppConstants.VIEW_INVENTORY;
	}

	/**
	 * 在庫削除処理（論理削除）
	 *
	 * @param selectedIds List Integer
	 * @param session HttpSession
	 * @param redirectAttributes RedirectAttributes
	 * @return String リダイレクト先
	 */
	@PostMapping(AppConstants.INVENTORY_DELETE_URL)
	public String deleteInventory(@RequestParam(required = false) List<Integer> selectedIds,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		// ログイン情報をセッションから取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		// ログイン情報取得に失敗時、ログイン画面に戻る
		if (loginUser == null) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_SYSTEM_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS,AppConstants.MESSAGE_BOX_SYSTEM_ERROR);
			return AppConstants.REDIRECT_LOGIN;
		}

		// 削除処理実行
		boolean message = inventoryService.logicalDelete(selectedIds, loginUser.getUserId());

		// 成否に応じてメッセージを設定
		if (message) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_INVENTORY_DELETE_SUCCESS);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_SUCCESS);
		} else {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_INVENTORY_DELETE_FAILED);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
		}

		return AppConstants.REDIRECT_INVENTORY;
	}
}
