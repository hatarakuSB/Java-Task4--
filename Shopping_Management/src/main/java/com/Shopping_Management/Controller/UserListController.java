package com.Shopping_Management.Controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.DTO.UserListDTO;
import com.Shopping_Management.Model.Service.UserListService;

import config.AppConstants;

@Controller
public class UserListController {

    private final UserListService userListService;

    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    /**
     * ユーザー一覧画面表示
     */
    @GetMapping(AppConstants.USER_LIST_URL)
    public String showUserList(HttpSession session, Model model) {
        // ログイン情報をセッションから取得
        LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
        model.addAttribute("loginUser", loginUser);

        // ページ名の取得
        model.addAttribute(AppConstants.ATTR_PAGE_TITLE, AppConstants.TITLE_USER_LIST);

        // ユーザー一覧を取得
        List<UserListDTO> users = userListService.findAllUsers();
        model.addAttribute(AppConstants.ATTR_USERS, users);

        return AppConstants.VIEW_USER_LIST;
    }

    /**
     * ユーザー削除処理
     */
    @PostMapping(AppConstants.USER_LIST_DELETE_URL)
    public String deleteUsers(@RequestParam(value = AppConstants.ATTR_DELETE_IDS, required = false) List<Integer> deleteIds,
                              Model model) {
        if (deleteIds == null || deleteIds.isEmpty()) {
            model.addAttribute(AppConstants.ATTR_USERS, userListService.findAllUsers());
            return AppConstants.VIEW_USER_LIST;
        }

        userListService.deleteUsersWithDetails(deleteIds);
        return AppConstants.REDIRECT_USER_LIST;
    }

    /**
     * CSVインポート処理
     */
    @PostMapping(AppConstants.USER_LIST_IMPORT_URL)
    public String importCsv(@RequestParam(AppConstants.ATTR_FILE) MultipartFile file,
                            RedirectAttributes redirectAttributes) {
        try {
            userListService.importUsersFromCsv(file);
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_CSV_IMPORT_SUCCESS);
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_CSV_IMPORT_FAILED);
            redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
        }
        return AppConstants.REDIRECT_USER_LIST;
    }
    
    /**
     * CSVエクスポート処理
     */
    @GetMapping(AppConstants.USER_LIST_EXPORT_URL)
    public void exportCsv(HttpServletResponse response) throws IOException {
        userListService.exportUsersToCsv(response);
    }
}
