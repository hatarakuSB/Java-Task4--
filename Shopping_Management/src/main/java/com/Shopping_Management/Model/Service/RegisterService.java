package com.Shopping_Management.Model.Service;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.RegisterDAO;

import parts.RegisterForm;

/**
 * 登録サービスクラス
 */
@Service
public class RegisterService {

    private final RegisterDAO registerDAO;

    public RegisterService(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }

    /**
     * 登録処理を実行
     *
     * @param form RegisterForm
     * @param userId int
     * @return boolean 登録成功なら true、失敗なら false
     */
    public boolean register(RegisterForm form, int userId) {
        return registerDAO.register(form, userId);
    }
}
