package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.PasswordChangeDAO;

import config.AppConstants;
import parts.PasswordChangeForm;

/**
 * パスワード変更サービスクラス
 */
@Service
public class PasswordChangeService {

	private final PasswordChangeDAO passwordChangeDAO;

	public PasswordChangeService(PasswordChangeDAO passwordChangeDAO) {
		this.passwordChangeDAO = passwordChangeDAO;
	}

	/**
	 * パスワード変更処理
	 *
	 * @param userId int
	 * @param form PasswordChangeForm
	 * @return String エラーメッセージ（成功時は null）
	 */
	public String changePassword(int userId, PasswordChangeForm form) {
		// 現在のパスワードを取得しチェック
		String currentPasswordInDb = passwordChangeDAO.getPasswordByUserId(userId);
		if (currentPasswordInDb == null || !currentPasswordInDb.equals(form.getCurrentPassword())) {
			return AppConstants.MSG_PASSWORD_INVALID_CURRENT;
		}

		// 新旧同一チェック
		if (form.getCurrentPassword().equals(form.getNewPassword())) {
			return AppConstants.MSG_PASSWORD_SAME_AS_OLD;
		}

		// 確認用パスワード一致チェック
		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			return AppConstants.MSG_PASSWORD_CONFIRM_MISMATCH;
		}

		// 更新処理
		int updated = passwordChangeDAO.updatePassword(userId, form.getNewPassword());
		if (updated == 0) {
			return AppConstants.MSG_PASSWORD_CHANGE_FAILED;
		}

		return null;
	}
}
