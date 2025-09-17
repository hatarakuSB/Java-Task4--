package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.PasswordChangeDAO;

import parts.PasswordChangeForm;

@Service
public class PasswordChangeService {

    private final PasswordChangeDAO passwordChangeDAO;

    public PasswordChangeService(PasswordChangeDAO passwordChangeDAO) {
        this.passwordChangeDAO = passwordChangeDAO;
    }

    public String changePassword(int userId, PasswordChangeForm form) {
        // DBから現在のパスワードを取得
        String currentPasswordInDb = passwordChangeDAO.getPasswordByUserId(userId);
        if (currentPasswordInDb == null || !currentPasswordInDb.equals(form.getCurrentPassword())) {
            return "現在のパスワードが正しくありません";
        }

        // 新旧同一チェック
        if (form.getCurrentPassword().equals(form.getNewPassword())) {
            return "新しいパスワードは現在のものと異なる値を設定してください";
        }

        // 確認用パスワード一致チェック
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            return "新規パスワードと確認用パスワードが一致しません";
        }

        // 更新処理
        int updated = passwordChangeDAO.updatePassword(userId, form.getNewPassword());
        if (updated == 0) {
            return "パスワード変更に失敗しました。";
        }

        return null; // 成功
    }
}
