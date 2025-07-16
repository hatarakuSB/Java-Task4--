package com.Shopping_Management.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Shopping_Management.Model.DAO.InventoryDAO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.PaginationHelper;

@Controller
public class InventoryController {

    private final InventoryDAO inventoryDAO;

    public InventoryController(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    @GetMapping("/Inventory")
    public String showInventory(
        @RequestParam(defaultValue = "0") int page,
        Model model) {
        
        ArrayList<LoginDTO> allItems = inventoryDAO.selectAll();
        int pageSize = 5;

        List<LoginDTO> pageItems = PaginationHelper.getPage(allItems, page, pageSize);

        model.addAttribute("items", pageItems);
        model.addAttribute("currentPage", page);
        model.addAttribute("hasPrev", page > 0);
        model.addAttribute("hasNext", (page + 1) * pageSize < allItems.size());

        return "Inventory";
    }

    @PostMapping("/Inventory/Delete")
    public String deleteInventory(@RequestParam(required = false) List<Integer> selectedIds) {
        if (selectedIds != null) {
            for (Integer id : selectedIds) {
                inventoryDAO.softDeleteById(id);
            }
            //後で消す。
            System.out.println("選択ID: " + selectedIds);
        }
        return "redirect:/Inventory";
    }

}

