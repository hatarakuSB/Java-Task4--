package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.NewUserDAO;

import parts.NewUserForm;

@Service
public class NewUserService {

	private final NewUserDAO userDAO;

	public NewUserService(NewUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * ユーザー登録処理
	 * @param userForm 入力データ
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String registerUser(NewUserForm userForm) {
		// ユーザー名重複チェック
		if (userDAO.existsByUsername(userForm.getUsername())) {
			return "このユーザー名は既に使われています";
		}

		// パスワード一致チェック
		if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
			return "確認用パスワードが一致しません";
		}

		// 登録処理
		userDAO.newUserInsert(userForm);
		return null;
	}
}