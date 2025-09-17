package com.Shopping_Management.Controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Shopping_Management.Model.DTO.UserListDTO;
import com.Shopping_Management.Model.Service.UserListService;

@Controller
@RequestMapping("/UserList")
public class UserListController {

    private final UserListService userListService;

    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    /**
     * ユーザー一覧画面表示
     */
    @GetMapping
    public String showUserList(Model model) {
        List<UserListDTO> users = userListService.findAllUsers();
        model.addAttribute("users", users);
        return "UserList";
    }

    /**
     * ユーザー削除処理
     */
    @PostMapping("/Delete")
    public String deleteUsers(@RequestParam(value = "deleteIds", required = false) List<Integer> deleteIds,
                              Model model) {
        if (deleteIds == null || deleteIds.isEmpty()) {
            model.addAttribute("errorMessage", "削除するユーザーを選択してください。");
            model.addAttribute("users", userListService.findAllUsers());
            return "UserList";
        }

        userListService.deleteUsersWithDetails(deleteIds);
        return "redirect:/UserList";
    }

    /**
     * CSVインポート処理
     */
    @PostMapping("/Import")
    public String importCsv(@RequestParam("file") MultipartFile file,
                            Model model) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("errorMessage", "CSVファイルを選択してください。");
                model.addAttribute("users", userListService.findAllUsers());
                return "UserList";
            }
            userListService.importUsersFromCsv(file);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "CSVインポートに失敗しました。");
            model.addAttribute("users", userListService.findAllUsers());
            return "UserList";
        }
        return "redirect:/UserList";
    }

    /**
     * CSVエクスポート処理
     */
    @GetMapping("/Export")
    public void exportCsv(HttpServletResponse response) throws IOException {
    	userListService.exportUsersToCsv(response);
    }
}
