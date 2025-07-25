package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DAO.RegisterDAO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.RegisterForm;

@Controller
public class RegisterController {
	RegisterDAO dao = new RegisterDAO();

	@GetMapping("/Register")
	public String showRegisterForm(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "Register";
	}

	@PostMapping("/Register")
	public String submitRegisterForm(@ModelAttribute("registerForm") @Valid RegisterForm registerForm,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession session) {  

		// セッションからログインユーザーを取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");

		// 未ログインならログイン画面へ
		if (loginUser == null) {
			return "redirect:/Login";
		}

		int userId = loginUser.getUserId(); 

		// バリデーションチェック
		if (bindingResult.hasErrors()) {
			return "Register";
		}

		// DAOにユーザーIDを渡して登録
		dao.register(registerForm, userId); 
		
		redirectAttributes.addFlashAttribute("successMessage", "登録が完了しました！");
		return "redirect:/Menu";
	}
}
