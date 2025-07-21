package com.Shopping_Management.Controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DAO.UserDAO;
import com.Shopping_Management.Model.DTO.UserDTO;

@Controller
public class NewUserController {

    private final UserDAO userDAO;

    public NewUserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/NewUser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "NewUser"; 
    }

    @PostMapping("/NewUser")
    public String registerUser(@Valid @ModelAttribute("userForm") UserDTO userForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "NewUser"; 
        }
        if (userDAO.existsByUsername(userForm.getUsername())) {
        	// ユーザー名が既に存在するか確認
            model.addAttribute("usernameError", "このユーザー名は既に使われています");
            return "NewUser";
        }
        if(!userForm.getPassword().equals(userForm.getConfirmPassword())) {
        	//パスワードと確認用パスワードが一致しているか確認
        	model.addAttribute("usernameError", "確認用パスワードが一致しません");
        	return "NewUser";
        }
        // 新規登録処理
        userDAO.insert(userForm);
        // 完了メッセージ表示してフォームを再表示
        model.addAttribute("successMessage", "登録が完了しました！");
        model.addAttribute("userForm", new UserDTO()); 
        return "NewUser";
        }
    }
