package parts;

import lombok.Data;

@Data
public class PasswordChangeForm {

	//現在のパスワード
	private String currentPassword;
	//新規パスワード
	private String newPassword;
	//確認用パスワード
	private String confirmPassword;
}
