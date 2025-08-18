package com.Shopping_Management.Controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Shopping_Management.Model.DTO.UserDTO;
import com.Shopping_Management.Model.Service.UserService;

@Controller
public class NewUserController {

    private final UserService userService;

    public NewUserController(UserService userService) {
        this.userService = userService;
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

        String errorMessage = userService.registerUser(userForm);

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "NewUser";
        }

        // 成功メッセージ
        model.addAttribute("successMessage", "登録が完了しました！");
        model.addAttribute("userForm", new UserDTO()); 
        return "NewUser";
    }
}
