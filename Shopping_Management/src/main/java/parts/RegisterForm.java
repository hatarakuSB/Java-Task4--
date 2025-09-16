package parts;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RegisterForm {
	
	//カテゴリ名
	private Integer  categoryId;
	
	//商品名
	private Integer  productId;
	
	//金額
	private String amount;
	
	//場所
	private String place;
	
	//日付
	private String buyDate;

}
