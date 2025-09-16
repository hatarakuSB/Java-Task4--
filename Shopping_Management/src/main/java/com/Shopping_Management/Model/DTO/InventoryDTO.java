package com.Shopping_Management.Model.DTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class InventoryDTO {
	// 商品ID（削除対象）
	private int detailId;
	//カテゴリ名
	private String categoryName;
	// 商品名
	private String productName; 
	// 登録日（購入日）
	private String latestDate; 
}
