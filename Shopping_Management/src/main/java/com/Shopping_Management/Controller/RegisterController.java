package com.Shopping_Management.Controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Shopping_Management.Model.DAO.PullDownDAO;
import com.Shopping_Management.Model.DTO.CategoryDTO;
import com.Shopping_Management.Model.DTO.LoginDTO;
import com.Shopping_Management.Model.DTO.ProductDTO;
import com.Shopping_Management.Model.Service.RegisterService;

import config.AppConstants;
import parts.RegisterForm;

/**
 * 登録コントローラークラス
 */
@Controller
public class RegisterController {
	
	private final PullDownDAO pullDownDAO;
	private final RegisterService registerService;

	public RegisterController(PullDownDAO pullDownDAO, RegisterService registerService) {
	    this.pullDownDAO = pullDownDAO;
	    this.registerService = registerService;
	}

	/**
	 * 登録画面を表示
	 *
	 * @param categoryId Integer
	 * @param productId Integer
	 * @param session HttpSession
	 * @param model Model
	 * @return String 遷移先ビュー名
	 */
	@GetMapping(AppConstants.REGISTER_URL)
	public String showRegisterForm(
	        @RequestParam(value = AppConstants.PARAM_CATEGORY_ID, required = false) Integer categoryId,
	        @RequestParam(value = AppConstants.PARAM_PRODUCT_ID, required = false) Integer productId,
	        HttpSession session,
	        Model model) {

	    // ログイン情報をセッションから取得
	    LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
	    if (loginUser == null) {
	        return AppConstants.REDIRECT_LOGIN;
	    }
	    model.addAttribute("loginUser", loginUser); 
	    
		// ページ名
	    model.addAttribute(AppConstants.ATTR_PAGE_TITLE, AppConstants.TITLE_REGISTER);

	    // カテゴリ一覧を取得
	    List<CategoryDTO> categoryList = pullDownDAO.findAllCategories();
	    model.addAttribute(AppConstants.ATTR_CATEGORY_LIST, categoryList);

	    // 商品一覧（カテゴリ選択時のみ）
	    List<ProductDTO> productList = new ArrayList<>();
	    if (categoryId != null) {
	        productList = pullDownDAO.findProductsByCategory(categoryId);
	        model.addAttribute(AppConstants.ATTR_SELECTED_CATEGORY_ID, categoryId);
	    }
	    model.addAttribute(AppConstants.ATTR_PRODUCT_LIST, productList);
	    
	    // RegisterForm を組み立て（カテゴリ・商品を保持して次のPOSTで利用）
	    RegisterForm form = new RegisterForm();
	    form.setCategoryId(categoryId);
	    form.setProductId(productId);
	    model.addAttribute(AppConstants.ATTR_REGISTER_FORM, form);
	    
	    if (productId != null) {
	    	model.addAttribute(AppConstants.ATTR_SELECTED_PRODUCT_ID, productId);	    
	    }
	    
	    return AppConstants.VIEW_REGISTER;
	}

	/**
	 * 登録処理を実行
	 *
	 * @param registerForm RegisterForm
	 * @param model Model
	 * @param redirectAttributes RedirectAttributes
	 * @param session HttpSession
	 * @return String 遷移先ビュー名
	 */
	@PostMapping(AppConstants.REGISTER_URL)
	public String submitRegisterForm(
	        @ModelAttribute(AppConstants.ATTR_REGISTER_FORM) @Valid RegisterForm registerForm,
	        Model model,
	        RedirectAttributes redirectAttributes,
	        HttpSession session) {
		
		// ログイン情報をセッションから取得
	    LoginDTO loginUser = (LoginDTO) session.getAttribute(AppConstants.SESSION_LOGIN_USER);
	    if (loginUser == null) {
	        return AppConstants.REDIRECT_LOGIN;
	    }
	    
	    // 登録処理実行
	    int userId = loginUser.getUserId();
	    boolean message = registerService.register(registerForm, userId);

	    // 結果メッセージを設定
	    if (message) {
	        redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_REGISTER_SUCCESS);
	        redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_SUCCESS);
	    } else {
	        redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MSG_REGISTER_FAILED);
	        redirectAttributes.addFlashAttribute(AppConstants.ATTR_MESSAGE_CLASS, AppConstants.MESSAGE_BOX_ERROR);
	    }

	    return AppConstants.REDIRECT_REGISTER;
	}
}
