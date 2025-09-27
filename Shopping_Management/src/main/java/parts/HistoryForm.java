package parts;

import lombok.Data;

/**
 * 履歴検索フォームクラス
 */
@Data
public class HistoryForm {

	/** カテゴリID */
	private Integer categoryId;

	/** 商品ID */
	private Integer productId;

	/** 場所 */
	private String place;

	/** 金額（下限） */
	private Integer moneyFrom;

	/** 金額（上限） */
	private Integer moneyTo;

	/** 日付（開始） */
	private String dateFrom;

	/** 日付（終了） */
	private String dateTo;
}
