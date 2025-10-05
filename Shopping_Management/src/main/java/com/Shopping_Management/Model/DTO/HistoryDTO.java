package com.Shopping_Management.Model.DTO;

import lombok.Data;

/**
 * 履歴DTOクラス
 */
@Data
public class HistoryDTO {

    /** 取引ID */
    private int tradeId;

    /** カテゴリ名 */
    private String categoryName;

    /** 商品名 */
    private String productName;

    /** 金額 */
    private int amount;

    /** 場所 */
    private String place;

    /** 購入日 */
    private String buyDate;
}
