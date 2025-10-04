package com.Shopping_Management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import config.AppConstants;

/**
 * 外部Todoアプリコントローラークラス
 */
@Controller
public class TodolAppController {

    /**
     * Todoアプリにリダイレクト
     *
     * @return String 遷移先リダイレクト先URL
     */
    @GetMapping(AppConstants.TODO_URL)
    public String redirectToTodo() {
        return AppConstants.REDIRECT_TODO_APP;
    }
}
