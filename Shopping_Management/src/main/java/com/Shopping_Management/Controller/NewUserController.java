package com.Shopping_Management.Controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.Service.NewUserService;

import config.AppConstants;
import parts.NewUserForm;

/**
 * 新規ユーザー登録コントローラークラス
 */
@Controller
public class NewUserController {

	private final NewUserService userService;

	public NewUserController(NewUserService userService) {
		this.userService = userService;
	}

	/**
	 * 新規ユーザー登録画面を表示
	 *
	 * @param model Model
	 * @return String 遷移先ビュー名
	 */
	@GetMapping(AppConstants.NEW_USER_URL)
	public String showRegistrationForm(Model model) {
		// 空のフォームを画面に渡す
		model.addAttribute(AppConstants.ATTR_NEW_USER_FORM, new NewUserForm());

		return AppConstants.VIEW_NEW_USER;
	}

	/**
	 * 新規ユーザーを登録
	 *
	 * @param userForm NewUserForm
	 * @param model Model
	 * @return String 遷移先ビュー名
	 */
	@PostMapping(AppConstants.NEW_USER_URL)
	public String registerUser(@Valid @ModelAttribute(AppConstants.ATTR_NEW_USER_FORM) NewUserForm userForm,
			Model model) {

		// サービスで登録処理を実行
		String message = userService.registerUser(userForm);

		// 成否に応じてメッセージを設定
		if (message != null) {
			model.addAttribute(AppConstants.ATTR_MESSAGE, message);
			model.addAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
		} else {
			model.addAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_NEW_USER_SUCCESS);
			model.addAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_SUCCESS);
		}

		// 再度空のフォームを渡し、入力欄を初期化
		model.addAttribute(AppConstants.ATTR_NEW_USER_FORM, new NewUserForm());

		return AppConstants.VIEW_NEW_USER;
	}
}
