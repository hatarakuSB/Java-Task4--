package parts;

import lombok.Data;

/**
 * ログイン画面入力フォームクラス
 */
@Data
public class LoginForm {

    /** ログインID */
    private String loUser;

    /** パスワード */
    private String loPass;
}
