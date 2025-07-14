package com.Shopping_Management.Controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DAO.LoginDAO;

import parts.LoginForm;

@Controller
public class LoginController {

	LoginDAO dao = new LoginDAO();
	
	@GetMapping("/")
	public String Login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "Login";
	}
	@PostMapping("/Login")
	public String login(@ModelAttribute("loginForm") @Valid LoginForm loginForm,
			BindingResult bindingResult,
			Model model) {
		try {
			//bindingResultで受け取ったバリデーション結果をhasErrorsでチェックする。
			if (bindingResult.hasErrors()) {
				return "Login";
			}
			else if(dao.select(loginForm)) {
				//TODO パスワードマスタチェック
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/Menu";
	}
}
