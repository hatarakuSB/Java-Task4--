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

/**
 * 履歴コントローラークラス
 */
@Controller
public class HistoryController {

	private final HistoryService historyService;
	private final PullDownDAO pullDownDAO;

	public HistoryController(HistoryService historyService, PullDownDAO pullDownDAO) {
		this.historyService = historyService;
		this.pullDownDAO = pullDownDAO;
	}

	/**
	 * 履歴検索画面を表示
	 *
	 * @param historyForm HistoryForm
	 * @param session HttpSession
	 * @param model Model
	 * @return String 遷移先ビュー名
	 */
	@GetMapping(AppConstants.HISTORY_URL)
	public String showHistoryForm(
			@ModelAttribute(AppConstants.ATTR_HISTORY_FORM) HistoryForm historyForm,
			HttpSession session,
			Model model) {

		// セッションからログインユーザーを取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		if (loginUser == null) {
			return AppConstants.REDIRECT_LOGIN;
		}
		model.addAttribute(AppConstants.ATTR_LOGIN_USER, loginUser);

		// ページタイトルを設定
		model.addAttribute(AppConstants.ATTR_PAGE_TITLE, AppConstants.TITLE_HISTORY);

		// カテゴリ一覧を取得
		List<CategoryDTO> categoryList = pullDownDAO.findAllCategories();
		model.addAttribute(AppConstants.ATTR_CATEGORY_LIST, categoryList);

		// 商品一覧（カテゴリ選択時のみ取得）
		List<ProductDTO> productList = new ArrayList<>();
		if (historyForm.getCategoryId() != null) {
			productList = pullDownDAO.findProductsByCategory(historyForm.getCategoryId());
			model.addAttribute(AppConstants.ATTR_SELECTED_CATEGORY_ID, historyForm.getCategoryId());
		}
		model.addAttribute(AppConstants.ATTR_PRODUCT_LIST, productList);

		// 商品選択済みの場合、商品情報を保持
		if (historyForm.getProductId() != null) {
			model.addAttribute(AppConstants.ATTR_SELECTED_PRODUCT_ID, historyForm.getProductId());
		}

		// itemsはFlashAttributeでセットされるので初期化のみ行う
		if (!model.containsAttribute(AppConstants.ATTR_ITEMS)) {
			model.addAttribute(AppConstants.ATTR_ITEMS, new ArrayList<HistoryDTO>());
		}

		// 履歴検索画面を表示
		return AppConstants.VIEW_HISTORY;
	}

	/**
	 * 履歴検索を実行
	 *
	 * @param historyForm HistoryForm
	 * @param redirectAttributes RedirectAttributes
	 * @param session HttpSession
	 * @return String 遷移先ビュー名
	 */
	@PostMapping(AppConstants.HISTORY_URL)
	public String submitHistoryForm(
			@ModelAttribute(AppConstants.ATTR_HISTORY_FORM) HistoryForm historyForm,
			RedirectAttributes redirectAttributes,
			HttpSession session) {

		// セッションからログインユーザーを取得
		LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
		if (loginUser == null) {
			return AppConstants.REDIRECT_LOGIN;
		}

		// 入力条件を検証
		List<String> errorMessages = historyService.validateConditions(historyForm);
		if (!errorMessages.isEmpty()) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, String.join("<br>", errorMessages));
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_HISTORY_FORM, historyForm);
			return AppConstants.REDIRECT_HISTORY;
		}

		// 履歴を検索
		List<HistoryDTO> results = historyService.search(historyForm, loginUser.getUserId());

		// 検索結果が空の場合、メッセージを設定
		String noDataMessage = historyService.checkNoData(results);
		if (noDataMessage != null) {
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, noDataMessage);
			redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
		}

		// 検索条件と結果をFlashAttributeに保存
		redirectAttributes.addFlashAttribute(AppConstants.ATTR_HISTORY_FORM, historyForm);
		redirectAttributes.addFlashAttribute(AppConstants.ATTR_ITEMS, results);

		// 履歴検索画面にリダイレクト
		return AppConstants.REDIRECT_HISTORY;
	}
}
