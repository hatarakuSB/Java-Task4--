package parts;

import lombok.Data;

/**
 * 新規ユーザー登録フォームクラス
 */
@Data
public class NewUserForm {

    /** ユーザー名 */
    private String username;

    /** パスワード */
    private String password;

    /** 確認用パスワード */
    private String confirmPassword;
}
