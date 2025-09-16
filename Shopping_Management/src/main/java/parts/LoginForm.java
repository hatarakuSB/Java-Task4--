package parts;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginForm {

	//ログインID
	private String loUser;

	//パスワード
	private String loPass;
}
