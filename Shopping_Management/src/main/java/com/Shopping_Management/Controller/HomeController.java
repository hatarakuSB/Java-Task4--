package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Shopping_Management.Model.DTO.LoginDTO;

import config.AppConstants;

/**
 * ホームコントローラークラス
 */
@Controller
public class HomeController {

	/**
	 * ホーム画面を表示
	 *
	 * @param session HttpSession
	 * @param model Model
	 * @return String 遷移先ビュー名
	 */
	@GetMapping(AppConstants.HOME_URL)
	public String Home(HttpSession session, Model model) {
		// ログイン情報をセッションから取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		if (loginUser == null) {
			return AppConstants.REDIRECT_LOGIN;
		}
		model.addAttribute(AppConstants.ATTR_LOGIN_USER, loginUser);

		// ページタイトルを設定
		model.addAttribute(AppConstants.ATTR_PAGE_TITLE, AppConstants.TITLE_HOME);

		// 権限を設定
		model.addAttribute(AppConstants.ATTR_AUTHORITY, loginUser.isAuthority());

		// ホーム画面を表示
		return AppConstants.VIEW_HOME;
	}
}
