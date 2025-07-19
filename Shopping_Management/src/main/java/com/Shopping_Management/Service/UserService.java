package com.Shopping_Management.Service;

import com.Shopping_Management.Model.DTO.UserDTO;

public interface UserService {
	// ユーザー名が使われてるか調べる
	 boolean isUsernameTaken(String username);   
	// 新しいユーザーを登録する
	 void registerNewUser(UserDTO userForm); 
}
