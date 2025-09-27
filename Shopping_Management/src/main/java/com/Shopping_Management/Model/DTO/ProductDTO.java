package com.Shopping_Management.Model.DTO;

import lombok.Data;

/**
 * 商品DTOクラス
 */
@Data
public class ProductDTO {

    /** 商品ID */
    private int productId;

    /** 商品名 */
    private String productName;
}
