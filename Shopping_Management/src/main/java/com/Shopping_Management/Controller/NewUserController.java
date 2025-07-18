package com.Shopping_Management.Controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DTO.UserDTO;

@Controller
public class NewUserController {
    @GetMapping("/NewUser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "NewUser"; 
    }
    // 登録処理の受け取り
    @PostMapping("/NewUser")
    public String registerUser(@Valid @ModelAttribute("userForm") UserDTO userForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
        	// 入力エラーがある場合、再表示
            return "NewUser"; 
        }

        if (UserService.isUsernameTaken(userForm.getUsername())) {
            model.addAttribute("usernameError", "このユーザー名は既に使われています");
            return "NewUser";
        }

        UserService.registerNewUser(userForm);
        return "redirect:/Login"; // 登録後はログイン画面へ
    }
}
