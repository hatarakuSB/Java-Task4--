package com.Shopping_Management.Controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Shopping_Management.Model.DAO.HistoryDAO;
import com.Shopping_Management.Model.DTO.HistoryDTO;
import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.PaginationHelper;
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
	    Model model,
	    HttpSession session) {
		
		// セッションからログインユーザーを取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");

		// 未ログインならログイン画面へ
		if (loginUser == null) {
			return "redirect:/Login";
		}

		int userId = loginUser.getUserId(); 

	    //日付ありなら日付で検索、なければ全件取得
	    ArrayList<HistoryDTO> allItems;
	    if (date != null && !date.isEmpty()) {
	        allItems = historyDAO.selectByDateAndUserId(date,userId);
	     // 検索条件の再表示用
	        model.addAttribute("searchDate", date); 
	    } else {
	        allItems = historyDAO.selectByUserId(userId);
	    }

	    int pageSize = 5;
	    List<HistoryDTO> pageItems = PaginationHelper.getPage(allItems, page, pageSize);

	    model.addAttribute("items", pageItems);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("hasNext", PaginationHelper.hasNext(allItems, page, pageSize));
	    model.addAttribute("hasPrev", PaginationHelper.hasPrev(page));

	    return "History";
	}

}
