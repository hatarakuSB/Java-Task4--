package config;

public class AppConstants {
    
	// URL
	public static final String LOGIN_URL = "/Login";
	public static final String HOME_URL = "/Home";
	public static final String REGISTER_URL = "/Register";
	public static final String HISTORY_URL = "/History";
	public static final String INVENTORY_URL = "/Inventory";
	public static final String NEW_USER_URL = "/NewUser";

	// ビュー名
	public static final String VIEW_LOGIN = "Login";
	public static final String VIEW_HOME = "Home";

	// リダイレクト
	public static final String REDIRECT_HOME = "redirect:" + HOME_URL;

	// モデル属性キー
	public static final String ATTR_LOGIN_FORM = "loginForm";
	public static final String ATTR_LOGIN_ERROR = "loginError";

	// メッセージ
	public static final String MSG_LOGIN_FAILED = "ユーザー名またはパスワードが違います";
}
