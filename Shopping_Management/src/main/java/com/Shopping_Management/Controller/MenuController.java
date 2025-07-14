package com.Shopping_Management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ch.qos.logback.core.model.Model;
import parts.LoginForm;

@Controller
public class MenuController {

	@GetMapping("/Menu")
	public String Menu(@ModelAttribute LoginForm loginForm, Model model) {

		return "Menu";
	}
}
