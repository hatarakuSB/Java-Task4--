package com.Shopping_Management.Model.DTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginDTO {
	
	private int id;
	
	private String productName;
	
	private String amount;
	
	private String place;
	
	private String buyDate;
	
	private boolean authority;
}
