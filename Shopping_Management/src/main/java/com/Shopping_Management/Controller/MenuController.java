package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Shopping_Management.Model.DTO.LoginDTO;

@Controller
public class MenuController {

	@GetMapping("/Menu")
	public String Menu(HttpSession session, Model model) {
		// ログイン情報をセッションから取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");

		// セッションが切れている、未ログイン → ログイン画面へリダイレクト
		if (loginUser == null) {
			return "redirect:/Login";
		}
		model.addAttribute("authority", loginUser.isAuthority());

		return "Menu";
	}
}
