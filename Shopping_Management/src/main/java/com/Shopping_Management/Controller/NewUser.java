package com.Shopping_Management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Shopping_Management.Model.DTO.UserDTO;

@Controller
public class NewUser {
    @GetMapping("/NewUser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "NewUser"; // templates/register.html を表示
    }
}
