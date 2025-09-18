package com.Shopping_Management.Model.DTO;

import lombok.Data;

@Data
public class UserListDTO {
	
	//チェックボックス用 USER_ID
	private int userId;
	
	// ユーザー名
	private String userName;
	
	// パスワード
	private String password;
}
