package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.NewUserDAO;

import config.AppConstants;
import parts.NewUserForm;

/**
 * 新規ユーザーサービスクラス
 */
@Service
public class NewUserService {

    private final NewUserDAO userDAO;

    public NewUserService(NewUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * ユーザー登録処理
     *
     * @param userForm NewUserForm
     * @return エラーメッセージ（成功時はnull）
     */
    public String registerUser(NewUserForm userForm) {
        // ユーザー名重複チェック
        if (userDAO.existsByUsername(userForm.getUsername())) {
            return AppConstants.MSG_NEW_USER_DUPLICATE;
        }

        // パスワード一致チェック
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            return AppConstants.MSG_NEW_USER_CONFIRM_PASSWORD_MISMATCH;
        }

        // 登録処理
        userDAO.newUserInsert(userForm);
        return null;
    }
}
