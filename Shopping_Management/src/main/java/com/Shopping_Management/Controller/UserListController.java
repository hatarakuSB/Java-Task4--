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
    @GetMapping("/UserList")
    public String showUserList(HttpSession session, Model model) {
        // ログイン情報をセッションから取得
        LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        // ページ名の取得
        model.addAttribute("pageTitle", AppConstants.TITLE_USER_LIST);

        // ユーザー一覧を取得
        List<UserListDTO> users = userListService.findAllUsers();
        model.addAttribute("users", users);

        return "UserList";
    }

    /**
     * ユーザー削除処理
     */
    @PostMapping("/UserList/Delete")
    public String deleteUsers(@RequestParam(value = "deleteIds", required = false) List<Integer> deleteIds,
                              Model model) {
        if (deleteIds == null || deleteIds.isEmpty()) {
            model.addAttribute("users", userListService.findAllUsers());
            return "UserList";
        }

        userListService.deleteUsersWithDetails(deleteIds);
        return "redirect:/UserList";
    }

    /**
     * CSVインポート処理
     */
    @PostMapping("/UserList/Import")
    public String importCsv(@RequestParam("file") MultipartFile file,
                            RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "ファイルを選択してください。");
                redirectAttributes.addFlashAttribute("messageClass", "message-box error-box");
                return "redirect:/UserList";
            }

            userListService.importUsersFromCsv(file);
            redirectAttributes.addFlashAttribute("message", "CSVをインポートしました。");
            redirectAttributes.addFlashAttribute("messageClass", "message-box success-box");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "CSVインポートに失敗しました。");
            redirectAttributes.addFlashAttribute("messageClass", "message-box error-box");
        }
        return "redirect:/UserList";
    }
    /**
     * CSVエクスポート処理
     */
    @GetMapping("/UserList/Export")
    public void exportCsv(HttpServletResponse response) throws IOException {
        userListService.exportUsersToCsv(response);
    }
}
