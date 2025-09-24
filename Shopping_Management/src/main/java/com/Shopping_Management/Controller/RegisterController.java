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

@Controller
public class RegisterController {
	
	private final PullDownDAO pullDownDAO;
	
	private final RegisterService registerService;

	public RegisterController(PullDownDAO pullDownDAO, RegisterService registerService) {
	    this.pullDownDAO = pullDownDAO;
	    this.registerService = registerService;
	}

	@GetMapping("/Register")
	public String showRegisterForm(
	        @RequestParam(value = "categoryId", required = false) Integer categoryId,
	        @RequestParam(value = "productId", required = false) Integer productId,
	        HttpSession session,
	        Model model) {

	    // ログイン情報をセッションから取得
	    LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
	    model.addAttribute("loginUser", loginUser); 
	    
		//ページ名の取得
		model.addAttribute("pageTitle", AppConstants.TITLE_REGISTER);

	    // カテゴリ一覧を取得
	    List<CategoryDTO> categoryList = pullDownDAO.findAllCategories();
	    model.addAttribute("categoryList", categoryList);

	    // 商品一覧をカテゴリ選択時のみ取得
	    List<ProductDTO> productList = new ArrayList<>();
	    if (categoryId != null) {
	        productList = pullDownDAO.findProductsByCategory(categoryId);
	        model.addAttribute("selectedCategoryId", categoryId);
	    }
	    model.addAttribute("productList", productList);
	    
	    // RegisterForm を組み立て（カテゴリ・商品を保持して次のPOSTで利用）
	    RegisterForm form = new RegisterForm();
	    form.setCategoryId(categoryId);
	    form.setProductId(productId);
	    model.addAttribute("registerForm", form);
	    
	    if (productId != null) {
	        model.addAttribute("selectedProductId", productId);
	    }
	    
	    return "Register";
	}


	@PostMapping("/Register")
	public String submitRegisterForm(
	        @ModelAttribute("registerForm") @Valid RegisterForm registerForm,
	        Model model,
	        RedirectAttributes redirectAttributes,
	        HttpSession session) {

	    LoginDTO loginUser = (LoginDTO) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/Login";
	    }

	    int userId = loginUser.getUserId();

	    boolean errorMessage = registerService.register(registerForm, userId);

	    if (errorMessage) {
	        redirectAttributes.addFlashAttribute("message", "登録が完了しました！");
	        redirectAttributes.addFlashAttribute("messageClass", "message-box success-box");
	    } else {
	        redirectAttributes.addFlashAttribute("message", "登録処理でエラーが発生しました。");
	        redirectAttributes.addFlashAttribute("messageClass", "message-box error-box");
	    }

	    return "redirect:/Register";
	}
}
