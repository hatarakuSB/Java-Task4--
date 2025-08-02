package com.Shopping_Management.Model.DTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class HistoryDTO {
	
	private int tradeId;
	
	private String productName;
	
	private String price;
	
	private String place;
	
	private String buyDate;
}
