package com.Shopping_Management.Model.DTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginDTO {

	private int userId;

	private String userName;
	
	private String password;
	
	private boolean authority;
	
	private boolean deleteFlag;

}
