package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.Service.PasswordChangeService;

import config.AppConstants;
import parts.PasswordChangeForm;

@Controller
public class PasswordChangeController {

    private final PasswordChangeService passwordChangeService;

    public PasswordChangeController(PasswordChangeService passwordChangeService) {
        this.passwordChangeService = passwordChangeService;
    }

    // 初期表示
    @GetMapping("/PasswordChange")
    public String showPasswordChangeForm(Model model, HttpSession session) {
        // ログイン情報をセッションから取得
        LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/Login"; // 未ログインならログイン画面へ
        }

        // ユーザー情報・ページ名をセット
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("pageTitle", AppConstants.TITLE_PASSWORD_CHANGE);

        // フォームの初期化
        model.addAttribute("passwordForm", new PasswordChangeForm());
        return "PasswordChange";
    }

    // パスワード変更処理
    @PostMapping("/PasswordChange")
    public String changePassword(@Valid @ModelAttribute("passwordForm") PasswordChangeForm passwordChangeForm,
                                 Model model,
                                 HttpSession session) {

        LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/Login";
        }

        // Service呼び出し
        String errorMessage = passwordChangeService.changePassword(loginUser.getUserId(), passwordChangeForm);

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "PasswordChange";
        }

        // 成功メッセージ
        model.addAttribute("successMessage", "パスワードを変更しました！");
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());
        return "PasswordChange";
    }
}
