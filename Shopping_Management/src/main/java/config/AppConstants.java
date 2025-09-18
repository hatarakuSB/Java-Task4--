package config;

public class AppConstants {

	// URL
	public static final String LOGIN_URL = "/Login";
	public static final String HOME_URL = "/Home";
	public static final String REGISTER_URL = "/Register";
	public static final String HISTORY_URL = "/History";
	public static final String INVENTORY_URL = "/Inventory";
	public static final String NEW_USER_URL = "/NewUser";
	public static final String USER_LIST_URL = "/UserList";

	// ビュー名
	public static final String VIEW_LOGIN = "Login";
	public static final String VIEW_HOME = "Home";

	// リダイレクト
	public static final String REDIRECT_HOME = "redirect:" + VIEW_HOME;

	// モデル属性キー
	public static final String ATTR_LOGIN_FORM = "loginForm";
	public static final String ATTR_LOGIN_ERROR = "loginError";

	// メッセージ
	public static final String MSG_LOGIN_FAILED = "ユーザー名またはパスワードが違います";

	//ページ名
	public static final String TITLE_HOME = "ホーム";
	public static final String TITLE_REGISTER = "登録";
	public static final String TITLE_HISTORY = "履歴";
	public static final String TITLE_INVENTORY = "在庫";
	public static final String TITLE_PASSWORD_CHANGE = "パスワード変更";
	public static final String TITLE_USER_LIST = "ユーザー一覧";
}
