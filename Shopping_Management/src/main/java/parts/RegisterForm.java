package parts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RegisterForm {
	
	//商品名
	@NotBlank(message = "商品名を入力してください")
	@Size(max = 10)
	private String ProductName;
	
	//金額
	@NotBlank(message = "金額を入力してください")
	@Pattern(regexp = "^[0-9]*$", message = "金額は数字のみ入力してください")
	@Size(max = 10)
	private String Amount;
	
	//場所
	@NotBlank(message = "場所を入力してください")
	@Size(max = 8)
	private String Place;
	
	//日付
	private String BuyDate;

}
