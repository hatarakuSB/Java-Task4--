package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.LoginDAO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

@Service
public class LoginService {
	private final LoginDAO loginDAO;

	public LoginService(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	/**
	 * ログインユーザー名・パスワードチェック
	 * @param loginForm 入力データ
	 * @return null(成功時はloginUser)
	 */
	public LoginDTO LoginUser(LoginForm loginForm) {
		LoginDTO loginUser = loginDAO.findByLoginForm(loginForm);

		// 認証失敗 or 論理削除済み
		if (loginUser == null || loginUser.isDeleteFlag()) {
			return null;
		}
		return loginUser;
	}
}
