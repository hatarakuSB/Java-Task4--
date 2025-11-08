package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.Service.LoginLogService;
import com.Shopping_Management.Model.Service.LoginService;

import config.AppConstants;
import parts.LoginForm;

/**
 * ログインコントローラークラス
 */
@Controller
public class LoginController {

	private final LoginService loginService;
	private final LoginLogService loginLogService;

	public LoginController(LoginService loginService, LoginLogService loginLogService) {
		this.loginService = loginService;
		this.loginLogService = loginLogService;
	}
	
	/**
	 * 初期画面を表示
	 *
	 * @return String 遷移先ビュー名
	 */
	@GetMapping("/")
	public String redirectToLogin() {
		return "redirect:" + AppConstants.LOGIN_URL;
	}

	/**
	 * ログイン画面を表示
	 *
	 * @param model Model
	 * @return String 遷移先ビュー名
	 */
	@GetMapping(AppConstants.LOGIN_URL)
	public String Login(Model model) {
		// 空のログインフォームを画面に渡す
		model.addAttribute(AppConstants.ATTR_LOGIN_FORM, new LoginForm());

		// ログイン画面を表示
		return AppConstants.VIEW_LOGIN;
	}

	/**
	 * ログイン処理
	 *
	 * @param loginForm LoginForm
	 * @param session HttpSession
	 * @param model Model
	 * @return String 遷移先ビュー名
	 */
	@PostMapping(AppConstants.LOGIN_URL)
	public String login(@ModelAttribute(AppConstants.ATTR_LOGIN_FORM) LoginForm loginForm,
			HttpSession session,
			Model model) {

		// 入力情報で認証処理を実行
		LoginDTO loginUser = loginService.LoginUser(loginForm);

		if (loginUser == null) {
			// 認証失敗、失敗ログを記録
			loginLogService.logFailure(loginForm.getLoUser());

			// エラーメッセージを設定
			model.addAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_LOGIN_FAILED);
			model.addAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);

			// ログイン画面に戻る
			return AppConstants.VIEW_LOGIN;
		}

		// 認証成功、成功ログを記録
		loginLogService.logSuccess(loginUser.getUserId(), loginUser.getUserName());

		// セッションにユーザー情報を保存
		session.setAttribute(AppConstants.SESSION_LOGIN_USER, loginUser);

		// ホーム画面へリダイレクト
		return AppConstants.REDIRECT_HOME;
	}
}
