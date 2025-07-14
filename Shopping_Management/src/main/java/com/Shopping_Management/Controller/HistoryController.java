package com.Shopping_Management.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Shopping_Management.Model.DAO.HistoryDAO;
import com.Shopping_Management.Model.DTO.LoginDTO;
@Controller
public class HistoryController {
	
	private final HistoryDAO historyDAO;
	
	public HistoryController(HistoryDAO historyDAO) {
		this.historyDAO = historyDAO;
	}
	@GetMapping("/History")
	public String search(
	    @RequestParam(required = false) String date,
	    @RequestParam(defaultValue = "0") int page,
	    Model model) {

	    ArrayList<LoginDTO> allItems = historyDAO.select();
	    
	    // ページネーション用：1ページあたり5件
	    int pageSize = 5;
	    int start = page * pageSize;
	    int end = Math.min(start + pageSize, allItems.size());
	    
	    List<LoginDTO> pageItems = allItems.subList(start, end);

	    model.addAttribute("items", pageItems);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("hasNext", end < allItems.size());
	    model.addAttribute("hasPrev", start > 0);

	    return "History";
	}

}
