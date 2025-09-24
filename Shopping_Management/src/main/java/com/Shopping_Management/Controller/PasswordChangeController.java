package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			RedirectAttributes redirectAttributes,
			HttpSession session) {

		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/Login";
		}

		// Service呼び出し
		String errorMessage = passwordChangeService.changePassword(loginUser.getUserId(), passwordChangeForm);

	    if (errorMessage != null) {
	        // エラー（現在のパスワード不一致など）
	        redirectAttributes.addFlashAttribute("message", errorMessage);
	        redirectAttributes.addFlashAttribute("messageClass", "message-box error-box");
	    } else {
	        // 成功
	        redirectAttributes.addFlashAttribute("message", "パスワードを変更しました");
	        redirectAttributes.addFlashAttribute("messageClass", "message-box success-box");
	    }

	    return "redirect:/PasswordChange";
	}}
