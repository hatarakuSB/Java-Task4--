package com.Shopping_Management.Model.DTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginDTO {
	
	public int ID;
	
	public String productName;
	
	public String amount;
	
	public String place;
	
	public String buyDate;
	
	public boolean Authority;
}
