package parts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginForm {

	//ログインID
	@NotBlank(message = "ログインIDを入力してください")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "ログインIDは英数字のみ入力してください")
	@Size(max = 8)
	private String loUser;

	//パスワード
	@NotBlank(message = "パスワードを入力してください")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "パスワードは英数字のみ入力してください")
	@Size(max = 8)
	private String loPass;
}
