package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.Service.LoginService;

import config.AppConstants;
import parts.LoginForm;

@Controller
public class LoginController {

	private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

	// 初期表示
	@GetMapping("/")
	public String Login(Model model) {
	    model.addAttribute(AppConstants.ATTR_LOGIN_FORM, new LoginForm());
	    return AppConstants.VIEW_LOGIN;
	}

	// ログイン処理
	@PostMapping(AppConstants.LOGIN_URL)
	public String login(@ModelAttribute(AppConstants.ATTR_LOGIN_FORM) @Valid LoginForm loginForm,
	                    BindingResult bindingResult,
	                    Model model,
	                    HttpSession session) {
	    if (bindingResult.hasErrors()) {
	        return AppConstants.VIEW_LOGIN;
	    }

	    LoginDTO loginUser = loginService.authenticate(loginForm);

	    if (loginUser == null) {
	        model.addAttribute(AppConstants.ATTR_LOGIN_ERROR, AppConstants.MSG_LOGIN_FAILED);
	        return AppConstants.VIEW_LOGIN;
	    }

	    session.setAttribute("loginUser", loginUser);
	    return AppConstants.REDIRECT_HOME; 
	}
}
