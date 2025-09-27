package com.Shopping_Management.Model.DTO;

import lombok.Data;

/**
 * 在庫DTOクラス
 */
@Data
public class InventoryDTO {

    /** 商品ID */
    private int detailId;

    /** カテゴリ名 */
    private String categoryName;

    /** 商品名 */
    private String productName;

    /** 登録日（購入日） */
    private String latestDate;
}
