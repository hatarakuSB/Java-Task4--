package com.Shopping_Management.Model.DTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginDTO {

	//ユーザーID
	private int userId;

	//ユーザ名
	private String userName;

	//パスワード
	private String password;

	//権限
	private boolean authority;

	//削除フラグ
	private boolean deleteFlag;
}
