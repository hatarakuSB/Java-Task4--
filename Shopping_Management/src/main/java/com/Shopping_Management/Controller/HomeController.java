package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Shopping_Management.Model.DTO.LoginDTO;

import config.AppConstants;

@Controller
public class HomeController {

	@GetMapping("/Home")
	public String Home(HttpSession session,Model model) {
		// ログイン情報をセッションから取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
		//ユーザー名の取得
		model.addAttribute("loginUser", loginUser); 
		//ページ名の取得
		model.addAttribute("pageTitle", AppConstants.TITLE_HOME);
		//権限の取得
		model.addAttribute("authority", loginUser.isAuthority());
		
		return "Home";
	}
}
