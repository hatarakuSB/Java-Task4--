package com.Shopping_Management.Model.DTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class InventoryDetailDTO {
	// 商品ID（削除対象）
	private int productId;
	// 商品名
	private String productName; 
	// 登録日（購入日）
	private String latestDate; 
    // 在庫数（登録件数）
    private int stockCount;
}
