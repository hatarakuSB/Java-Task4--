package com.Shopping_Management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.model.Model;
@Controller
public class InventoryController {
	@PostMapping("/Inventory")
	public String Inventory(Model model) {
		return "Inventory.html";
	}
}
