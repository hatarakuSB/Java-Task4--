package com.Shopping_Management.Controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.Service.NewUserService;

import parts.NewUserForm;

@Controller
public class NewUserController {

    private final NewUserService userService;

    public NewUserController(NewUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/NewUser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new NewUserForm());
        return "NewUser"; 
    }

    @PostMapping("/NewUser")
    public String registerUser(@Valid @ModelAttribute("userForm") NewUserForm userForm,
                               Model model) {

        String errorMessage = userService.registerUser(userForm);

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "NewUser";
        }

        // 成功メッセージ
        model.addAttribute("successMessage", "登録が完了しました！");
        model.addAttribute("userForm", new NewUserForm()); 
        return "NewUser";
    }
}
