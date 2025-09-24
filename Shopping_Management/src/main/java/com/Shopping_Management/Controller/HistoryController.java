package com.Shopping_Management.Controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DAO.PullDownDAO;
import com.Shopping_Management.Model.DTO.CategoryDTO;
import com.Shopping_Management.Model.DTO.HistoryDTO;
import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.DTO.ProductDTO;
import com.Shopping_Management.Model.Service.HistoryService;

import config.AppConstants;
import parts.HistoryForm;

@Controller
public class HistoryController {

	private final HistoryService historyService;

	private final PullDownDAO pullDownDAO;

	public HistoryController(HistoryService historyService, PullDownDAO pullDownDAO) {
		this.historyService = historyService;
		this.pullDownDAO = pullDownDAO;
	}

	@GetMapping("/History")
	public String showHistoryForm(
			@ModelAttribute("historyForm") HistoryForm historyForm,
			HttpSession session,
			Model model) {

		// セッションからログインユーザーを取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/Login";
		}
		model.addAttribute("loginUser", loginUser);

		// ページ名
		model.addAttribute("pageTitle", AppConstants.TITLE_HISTORY);

		// カテゴリ一覧を取得
		List<CategoryDTO> categoryList = pullDownDAO.findAllCategories();
		model.addAttribute("categoryList", categoryList);

		// 商品一覧（カテゴリ選択時のみ）
		List<ProductDTO> productList = new ArrayList<>();
		if (historyForm.getCategoryId() != null) {
			productList = pullDownDAO.findProductsByCategory(historyForm.getCategoryId());
			model.addAttribute("selectedCategoryId", historyForm.getCategoryId());
		}
		model.addAttribute("productList", productList);

		if (historyForm.getProductId() != null) {
			model.addAttribute("selectedProductId", historyForm.getProductId());
		}

		// items は FlashAttribute でセットされるので特に初期化不要
		if (!model.containsAttribute("items")) {
			model.addAttribute("items", new ArrayList<HistoryDTO>()); 
		}

		return "History";
	}

	@PostMapping("/History")
	public String submitHistoryForm(
			@ModelAttribute("historyForm") HistoryForm historyForm,
			RedirectAttributes redirectAttributes,
			HttpSession session) {

		// セッションからログインユーザーを取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/Login";
		}

		// 相関チェック
		List<String> errorMessages = historyService.validateConditions(historyForm);
		if (!errorMessages.isEmpty()) {
	        redirectAttributes.addFlashAttribute("message", String.join("<br>", errorMessages));
	        redirectAttributes.addFlashAttribute("messageClass", "message-box error-box");
		    redirectAttributes.addFlashAttribute("historyForm", historyForm);
		    return "redirect:/History";
		}

		// 検索実行
		List<HistoryDTO> results = historyService.search(historyForm, loginUser.getUserId());

		// 結果チェック
	    String noDataMessage = historyService.checkNoData(results);
	    if (noDataMessage != null) {
	        redirectAttributes.addFlashAttribute("message", noDataMessage);
	        redirectAttributes.addFlashAttribute("messageClass", "message-box error-box");
	        }
	    
		// 検索条件と結果を FlashAttribute に保存
		redirectAttributes.addFlashAttribute("historyForm", historyForm);
		redirectAttributes.addFlashAttribute("items", results);

		return "redirect:/History";
	}
}
