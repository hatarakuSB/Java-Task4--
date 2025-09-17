package parts;

import lombok.Data;

@Data
public class HistoryForm {
	private Integer categoryId;      // カテゴリID
    private Integer productId;   // 商品ID
    private String place;          // 場所
    private Integer moneyFrom;     // 金額(下限)
    private Integer moneyTo;       // 金額(上限)
    private String dateFrom;       // 日付(開始)
    private String dateTo;         // 日付(終了)
}
