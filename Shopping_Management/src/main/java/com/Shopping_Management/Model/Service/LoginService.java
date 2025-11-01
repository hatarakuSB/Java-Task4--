package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.LoginDAO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

/**
 * ログイン認証サービスクラス
 */
@Service
public class LoginService {

	private final LoginDAO loginDAO;

	public LoginService(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	/**
	 * ログイン認証処理
	 *
	 * @param loginForm 
	 * @return loginUser
	 */
	public LoginDTO LoginUser(LoginForm loginForm) {
		// 入力フォームのユーザー名・パスワードでユーザー検索
		LoginDTO loginUser = loginDAO.findByLoginForm(loginForm);

		// 認証失敗時、nullを返す
		if (loginUser == null) {
			return null;
		}

		// 認証成功時はユーザー情報を返す
		return loginUser;
	}
}
