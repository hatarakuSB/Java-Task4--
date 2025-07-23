package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DAO.LoginDAO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

@Controller
public class LoginController {

	private final LoginDAO loginDAO;

	public LoginController(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	// 初期表示
	@GetMapping("/")
	public String Login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "Login";
	}

	// 新規登録からログイン画面に戻る
	@GetMapping("/Login")
	public String showLoginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "Login";
	}

	// ログインボタン押下
	@PostMapping("/Login")
	public String login(@ModelAttribute("loginForm") @Valid LoginForm loginForm,
	                    BindingResult bindingResult,
	                    Model model,
	                    HttpSession session) {
	    if (bindingResult.hasErrors()) {
	        return "Login";
	    }

	    LoginDTO loginUser = loginDAO.findByLoginForm(loginForm);

	    // 認証失敗 or 論理削除済み
	    if (loginUser == null || loginUser.isDeleteFlag()) {
	        model.addAttribute("loginError", "ユーザー名またはパスワードが違います");
	        return "Login";
	    }

	    // セッションにユーザー情報を保存
	    session.setAttribute("loginUser", loginUser);

	    return "redirect:/Menu";
	}
}
