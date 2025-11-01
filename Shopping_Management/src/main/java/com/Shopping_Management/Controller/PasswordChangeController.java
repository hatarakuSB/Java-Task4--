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

/**
 * パスワード変更コントローラークラス
 */
@Controller
public class PasswordChangeController {

    private final PasswordChangeService passwordChangeService;

    public PasswordChangeController(PasswordChangeService passwordChangeService) {
        this.passwordChangeService = passwordChangeService;
    }

    /**
     * パスワード変更画面を表示
     *
     * @param model Model
     * @param session HttpSession
     * @return String 遷移先ビュー名
     */
    @GetMapping(AppConstants.PASSWORD_CHANGE_URL)
    public String showPasswordChangeForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    	
    	// ユーザー情報をセッションから取得
        LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		// ログイン情報取得に失敗時、ログイン画面に戻る
		if (loginUser == null) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_SYSTEM_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS,AppConstants.MESSAGE_BOX_SYSTEM_ERROR);
			return AppConstants.REDIRECT_LOGIN;
		}
        model.addAttribute(AppConstants.ATTR_LOGIN_USER, loginUser);
        
		// 権限を設定
		model.addAttribute(AppConstants.ATTR_AUTHORITY, loginUser.isAuthority());

		// ページタイトルを設定
        model.addAttribute(AppConstants.ATTR_PAGE_TITLE, AppConstants.TITLE_PASSWORD_CHANGE);

        // 入力フォームを初期化
        model.addAttribute(AppConstants.ATTR_PASSWORD_CHANGE_FORM, new PasswordChangeForm());

        return AppConstants.VIEW_PASSWORD_CHANGE;
    }

    /**
     * パスワード変更処理
     *
     * @param passwordChangeForm PasswordChangeForm
     * @param redirectAttributes RedirectAttributes
     * @param session HttpSession
     * @return String リダイレクト先
     */
    @PostMapping(AppConstants.PASSWORD_CHANGE_URL)
    public String changePassword(
            @Valid @ModelAttribute(AppConstants.ATTR_PASSWORD_CHANGE_FORM) PasswordChangeForm passwordChangeForm,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

    	// ユーザー情報をセッションから取得
        LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		// ログイン情報取得に失敗時、ログイン画面に戻る
		if (loginUser == null) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_SYSTEM_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS,AppConstants.MESSAGE_BOX_SYSTEM_ERROR);
			return AppConstants.REDIRECT_LOGIN;
		}

        // サービス呼び出しでパスワード変更を実行
        String message = passwordChangeService.changePassword(loginUser.getUserId(), passwordChangeForm);

        // 成否に応じてメッセージを設定
        if (message != null) {
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, message);
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
        } else {
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_PASSWORD_CHANGE_SUCCESS);
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_SUCCESS);
        }

        return AppConstants.REDIRECT_PASSWORD_CHANGE;
    }
}
