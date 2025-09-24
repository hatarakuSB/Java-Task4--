package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.Service.LoginLogService;
import com.Shopping_Management.Model.Service.LoginService;

import config.AppConstants;
import parts.LoginForm;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final LoginLogService loginLogService; 

    public LoginController(LoginService loginService, LoginLogService loginLogService) {
        this.loginService = loginService;
        this.loginLogService = loginLogService;
    }

    // 初期表示
    @GetMapping({"/","/Login"})
    public String Login(Model model) {
        model.addAttribute(AppConstants.ATTR_LOGIN_FORM, new LoginForm());
        return AppConstants.VIEW_LOGIN;
    }

    // ログイン処理
    @PostMapping(AppConstants.LOGIN_URL)
    public String login(@ModelAttribute(AppConstants.ATTR_LOGIN_FORM) @Valid LoginForm loginForm,
                        HttpSession session,
                        Model model) {

        LoginDTO loginUser = loginService.LoginUser(loginForm);

        if (loginUser == null) {
            // 失敗ログ（USER_IDは取得できない為、ユーザー名の入力値を保存）
            loginLogService.logFailure(loginForm.getLoUser());

            model.addAttribute("message", AppConstants.MSG_LOGIN_FAILED);
            model.addAttribute("messageClass", "message-box error-box");
            return AppConstants.VIEW_LOGIN;
        }

        // 成功ログ
        loginLogService.logSuccess(loginUser.getUserId(), loginUser.getUserName());

        session.setAttribute("loginUser", loginUser);
        return AppConstants.REDIRECT_HOME;
    }
}
