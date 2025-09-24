package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.Service.LoginLogService; // ← 追加

import config.AppConstants;

@Controller
public class LogoutController {

    private final LoginLogService loginLogService;

    public LogoutController(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @GetMapping("/Logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // ユーザー情報を取得
        LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");

        if (loginUser != null) {
            // ログアウトログを記録
            loginLogService.logLogout(loginUser.getUserId(), loginUser.getUserName());
        }

        // セッション破棄
        session.invalidate();

        // ログアウトメッセージをセット
        redirectAttributes.addFlashAttribute("message", "ログアウトされました");
        redirectAttributes.addFlashAttribute("messageClass", "message-box logout-box");

        // ログイン画面へリダイレクト
        return "redirect:" + AppConstants.LOGIN_URL;
    }
}
