package com.Shopping_Management.Controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.DTO.UserListDTO;
import com.Shopping_Management.Model.Service.UserListService;

import config.AppConstants;

/**
 * ユーザー一覧コントローラー
 */
@Controller
public class UserListController {

	private final UserListService userListService;

	public UserListController(UserListService userListService) {
		this.userListService = userListService;
	}

	/**
	 * ユーザー一覧を表示
	 *
	 * @param session HttpSession
	 * @param model Model
	 * @return String遷移先ビュー名
	 */
	@GetMapping(AppConstants.USER_LIST_URL)
	public String showUserList(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

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

		// ページ名の取得
		model.addAttribute(AppConstants.ATTR_PAGE_TITLE, AppConstants.TITLE_USER_LIST);

		// ユーザー一覧を取得
		List<UserListDTO> users = userListService.findAllUsers();
		model.addAttribute(AppConstants.ATTR_USERS, users);

		return AppConstants.VIEW_USER_LIST;
	}

	/**
	 * ユーザー削除処理
	 *
	 * @param deleteIds List<Integer> 
	 * @param model Model 
	 * @return String 遷移先ビュー名
	 */
	@PostMapping(AppConstants.USER_LIST_DELETE_URL)
	public String deleteUsers(
			@RequestParam(value = AppConstants.ATTR_DELETE_IDS, required = false) List<Integer> deleteIds,
			RedirectAttributes redirectAttributes,
			HttpSession session,
			Model model) {
		//ユーザー削除失敗時、エラーメッセージを返却
		if (deleteIds == null || deleteIds.isEmpty()) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_SYSTEM_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS,AppConstants.MESSAGE_BOX_SYSTEM_ERROR);
			session.invalidate();
			return AppConstants.REDIRECT_LOGIN;
		}

		userListService.deleteUsersWithDetails(deleteIds);
		//ユーザー削除成功メッセージを表示
		redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_USER_LIST_DELETE_SUCCESS);
		redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_SUCCESS);

		return AppConstants.REDIRECT_USER_LIST;
	}

	/**
	 * CSVインポート処理
	 *
	 * @param file MultipartFile 
	 * @param redirectAttributes RedirectAttributes 
	 * @return String 遷移先ビュー名
	 */
	@PostMapping(AppConstants.USER_LIST_IMPORT_URL)
	public String importCsv(@RequestParam(AppConstants.ATTR_FILE) MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			String errors = userListService.importUsersFromCsv(file);

			if (errors != null) {
				redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, errors);
				redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
			} else {
				redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_CSV_IMPORT_SUCCESS);
				redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_SUCCESS);
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_CSV_IMPORT_FAILED);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
		}
		return AppConstants.REDIRECT_USER_LIST;
	}

	/**
	 * CSVをエクスポート
	 *
	 * @param response HttpServletResponse 
	 * @throws IOException 
	 */
	@GetMapping(AppConstants.USER_LIST_EXPORT_URL)
	public void exportCsv(HttpServletResponse response) throws IOException {
		userListService.exportUsersToCsv(response);
	}
}
