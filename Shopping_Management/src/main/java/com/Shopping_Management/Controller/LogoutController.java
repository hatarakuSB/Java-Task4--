package com.Shopping_Management.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.Service.LoginLogService;

import config.AppConstants;

/**
 * ログアウト処理コントローラークラス
 */
@Controller
public class LogoutController {

    private final LoginLogService loginLogService;

    public LogoutController(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    /**
     * ログアウト処理
     *
     * @param session HttpSession
     * @param redirectAttributes RedirectAttributes
     * @return String ログイン画面へリダイレクト
     */
    @GetMapping(AppConstants.LOGOUT_URL)
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // ユーザー情報を取得
        LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		// ログイン情報取得に失敗時、ログイン画面に戻る
		if (loginUser == null) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_SYSTEM_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS,AppConstants.MESSAGE_BOX_SYSTEM_ERROR);
			return AppConstants.REDIRECT_LOGIN;
		}

        if (loginUser != null) {
            // ログアウトログを記録
            loginLogService.logLogout(loginUser.getUserId(), loginUser.getUserName());
        }

        // セッション破棄
        session.invalidate();

        // ログアウト完了メッセージをセット
        redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_LOGOUT);
        redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_LOGOUT);

        // ログイン画面へリダイレクト
        return AppConstants.REDIRECT_LOGIN;
    }
}
