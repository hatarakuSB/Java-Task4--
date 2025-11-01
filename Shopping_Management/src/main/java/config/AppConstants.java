package config;

public class AppConstants {

	//URL
	public static final String LOGIN_URL = "/Login";
	public static final String LOGOUT_URL = "/Logout";
	public static final String HOME_URL = "/Home";
	public static final String REGISTER_URL = "/Register";
	public static final String HISTORY_URL = "/History";
	public static final String INVENTORY_URL = "/Inventory";
	public static final String INVENTORY_DELETE_URL = "/Inventory/Delete";
	public static final String NEW_USER_URL = "/NewUser";
	public static final String USER_LIST_URL = "/UserList";
	public static final String USER_LIST_DELETE_URL = "/UserList/Delete";
	public static final String USER_LIST_IMPORT_URL = "/UserList/Import";
	public static final String USER_LIST_EXPORT_URL = "/UserList/Export";
	public static final String PASSWORD_CHANGE_URL = "/PasswordChange";
	public static final String TODO_URL = "/Todo";
	public static final String TODO_APP_URL = "http://localhost:8080";

	//画面ビュー名
	public static final String VIEW_LOGIN = "Login";
	public static final String VIEW_HOME = "Home";
	public static final String VIEW_REGISTER = "Register";
	public static final String VIEW_HISTORY = "History";
	public static final String VIEW_INVENTORY = "Inventory";
	public static final String VIEW_NEW_USER = "NewUser";
	public static final String VIEW_USER_LIST = "UserList";
	public static final String VIEW_PASSWORD_CHANGE = "PasswordChange";

	//リダイレクトURL
	public static final String REDIRECT_LOGIN = "redirect:" + LOGIN_URL;
	public static final String REDIRECT_LOGOUT = "redirect:" + LOGOUT_URL;
	public static final String REDIRECT_HOME = "redirect:" + HOME_URL;
	public static final String REDIRECT_REGISTER = "redirect:" + REGISTER_URL;
	public static final String REDIRECT_HISTORY = "redirect:" + HISTORY_URL;
	public static final String REDIRECT_INVENTORY = "redirect:" + INVENTORY_URL;
	public static final String REDIRECT_USER_LIST = "redirect:" + USER_LIST_URL;
	public static final String REDIRECT_PASSWORD_CHANGE = "redirect:" + PASSWORD_CHANGE_URL;
	public static final String REDIRECT_TODO_APP = "redirect:" + TODO_APP_URL;

	//セッションキー
	public static final String SESSION_LOGIN_USER = "loginUser";

	//モデル属性キー
	public static final String ATTR_LOGIN_FORM = "loginForm";
	public static final String ATTR_LOGIN_ERROR = "loginError";
	public static final String ATTR_NEW_USER_FORM = "userForm";
	public static final String ATTR_REGISTER_FORM = "registerForm";
	public static final String ATTR_HISTORY_FORM = "historyForm";
	public static final String ATTR_INVENTORY_FORM = "inventoryForm";
	public static final String ATTR_PASSWORD_CHANGE_FORM = "passwordForm";

	public static final String ATTR_LOGIN_USER = "loginUser";
	public static final String ATTR_PAGE_TITLE = "pageTitle";
	public static final String ATTR_AUTHORITY = "authority";
	public static final String ATTR_CATEGORY_LIST = "categoryList";
	public static final String ATTR_PRODUCT_LIST = "productList";
	public static final String ATTR_SELECTED_PRODUCT_ID = "selectedProductId";
	public static final String ATTR_SELECTED_CATEGORY_ID = "selectedCategoryId";
	public static final String ATTR_USERS = "users";
	public static final String ATTR_USER_LIST = "userList";
	public static final String ATTR_DELETE_IDS = "deleteIds";
	public static final String ATTR_FILE = "file";
	public static final String ATTR_ITEMS = "items";
	public static final String ATTR_MESSAGE = "message";
	public static final String ATTR_MESSAGE_CLASS = "messageClass";

	//共通メッセージ
	public static final String MSG_LOGIN_FAILED = "ユーザー名またはパスワードが違います";
	public static final String MSG_LOGOUT = "ログアウトされました";
	public static final String MSG_SYSTEM_ERROR = "システムエラーが起きました。ログイン画面に戻ります。";

	//新規ユーザーメッセージ
	public static final String MSG_NEW_USER_SUCCESS = "登録が完了しました";
	public static final String MSG_NEW_USER_DUPLICATE = "このユーザー名は既に使われています";
	public static final String MSG_NEW_USER_CONFIRM_PASSWORD_MISMATCH = "確認用パスワードが一致しません";

	//登録画面メッセージ
	public static final String MSG_REGISTER_SUCCESS = "登録が完了しました";
	public static final String MSG_REGISTER_FAILED = "登録に失敗しました";

	//パスワード変更メッセージ
	public static final String MSG_PASSWORD_CHANGE_SUCCESS = "パスワードを変更しました";
	public static final String MSG_PASSWORD_CHANGE_FAILED = "パスワードの変更に失敗しました";
	public static final String MSG_PASSWORD_INVALID_CURRENT = "現在のパスワードが正しくありません";
	public static final String MSG_PASSWORD_SAME_AS_OLD = "新しいパスワードは現在のパスワードと異なるパスワードに設定してください";

	//履歴画面メッセージ
	public static final String MSG_HISTORY_INVALID_MONEY_RANGE = "金額（下限）は金額（上限）以下を入力してください。";
	public static final String MSG_HISTORY_INVALID_DATE_RANGE = "日付（開始日）は日付（終了日）以前を入力してください。";
	public static final String MSG_HISTORY_INVALID_DATE_FORMAT = "日付の形式が不正です。（yyyy/MM/dd 形式で入力してください）";
	public static final String MSG_NO_DATA = "該当するデータはありません。";

	//在庫画面メッセージ
	public static final String MSG_INVENTORY_DELETE_SUCCESS = "選択した商品を削除しました。";
	public static final String MSG_INVENTORY_DELETE_FAILED = "削除処理でエラーが発生しました。";

	//ユーザー一覧メッセージ
	public static final String MSG_USER_LIST_DELETE_SUCCESS ="ユーザーの削除が完了しました。";
	public static final String MSG_CSV_IMPORT_SUCCESS = "CSVインポートが完了しました";
	public static final String MSG_CSV_IMPORT_FAILED = "CSVインポートに失敗しました";
	public static final String MSG_USER_LIST_FILE_EMPTY = "ファイルを選択してください。";
	public static final String MSG_USER_LIST_IMPORT_SUCCESS = "CSVをインポートしました。";
	public static final String MSG_USER_LIST_IMPORT_FAILED = "CSVインポートに失敗しました。";

	//ページタイトル
	public static final String TITLE_HOME = "ホーム";
	public static final String TITLE_REGISTER = "登録";
	public static final String TITLE_HISTORY = "履歴";
	public static final String TITLE_INVENTORY = "在庫";
	public static final String TITLE_PASSWORD_CHANGE = "パスワード変更";
	public static final String TITLE_USER_LIST = "ユーザー一覧";
	public static final String TITLE_NEW_USER = "新規ユーザー登録";

	//メッセージ用CSSクラス
	public static final String MESSAGE_BOX_ERROR = "message-box error-box";
	public static final String MESSAGE_BOX_SUCCESS = "message-box success-box";
	public static final String MESSAGE_BOX_LOGOUT = "message-box logout-box";
	public static final String MESSAGE_BOX_SYSTEM_ERROR = "message-box system_error-box";


	//リクエストパラメータキー
	public static final String PARAM_CATEGORY_ID = "categoryId";
	public static final String PARAM_PRODUCT_ID = "productId";

	//共通フォーマット 
	public static final String DATE_FORMAT = "yyyy/MM/dd";

	// CSV
	public static final String CSV_CONTENT_TYPE = "text/csv; charset=UTF-8";
	public static final String CSV_USER_LIST_FILENAME = "user_list.csv";
	public static final String CSV_USER_LIST_HEADER = "USER_ID,USER_NAME,PASSWORD";

	//ログ関連
	public static final String LOG_DIR = "logs";

	// ログイベントコード
	public static final String LOG_EVENT_SUCCESS = "01";
	public static final String LOG_EVENT_FAILURE = "02";
	public static final String LOG_EVENT_LOGOUT = "03";

	// ログイベント名
	public static final String LOG_EVENT_NAME_SUCCESS = "成功";
	public static final String LOG_EVENT_NAME_FAILURE = "失敗";
	public static final String LOG_EVENT_NAME_LOGOUT = "ログアウト";

	// ログ用日付フォーマット
	public static final String DATE_FORMAT_LOG_FILE = "yyyy-MM-dd";
	public static final String DATE_FORMAT_LOG_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
}
