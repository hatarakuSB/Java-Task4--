package com.Shopping_Management.Model.DTO;

import lombok.Data;

@Data
public class UserListDTO {
	private int userId;       // ユーザーID (PK)
    private String userName;  // ユーザー名
    private String password;  // パスワード
}
