package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.LoginDAO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDAO loginDAO;

    public LoginServiceImpl(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Override
    public LoginDTO authenticate(LoginForm loginForm) {
        LoginDTO loginUser = loginDAO.findByLoginForm(loginForm);

        // 認証失敗 or 論理削除済み
        if (loginUser == null || loginUser.isDeleteFlag()) {
            return null;
        }
        return loginUser;
    }
}
