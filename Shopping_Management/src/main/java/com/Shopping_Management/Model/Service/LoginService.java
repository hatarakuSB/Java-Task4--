package com.Shopping_Management.Model.Service;

import com.Shopping_Management.Model.DTO.LoginDTO;

import parts.LoginForm;

public interface LoginService {
	LoginDTO authenticate(LoginForm loginForm);
}
