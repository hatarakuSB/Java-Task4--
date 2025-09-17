package parts;

import lombok.Data;

@Data
public class NewUserForm {
	
	//ユーザ名
	private String username;
	
	//パスワード
	private String password;
	
	//新規パスワード
	private String confirmPassword;
}
