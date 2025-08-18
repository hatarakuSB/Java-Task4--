package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.UserDAO;
import com.Shopping_Management.Model.DTO.UserDTO;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * ユーザー登録処理
     * @param userForm 入力データ
     * @return エラーメッセージ（成功時はnull）
     */
    public String registerUser(UserDTO userForm) {
        // ユーザー名重複チェック
        if (userDAO.existsByUsername(userForm.getUsername())) {
            return "このユーザー名は既に使われています";
        }

        // パスワード一致チェック
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            return "確認用パスワードが一致しません";
        }

        // 登録処理
        userDAO.insert(userForm);
        return null; 
    }
}