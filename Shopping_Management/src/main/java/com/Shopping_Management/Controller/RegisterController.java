package com.Shopping_Management.Controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DAO.RegisterDAO;

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
			RedirectAttributes redirectAttributes) {
		//bindingResultで受け取ったバリデーション結果をhasErrorsでチェックする。
		if (bindingResult.hasErrors()) {
			return "Register";
		}
		dao.register(registerForm);
		redirectAttributes.addFlashAttribute("successMessage", "登録が完了しました！");
		return "redirect:/Menu";
	}
}
