package com.Shopping_Management.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "ユーザー名は必須です")
    @Size(max = 50, message = "ユーザー名は20文字以内で入力してください")
    private String username;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 6, message = "パスワードは8文字以内で入力してください")
    private String password;

    @NotBlank(message = "確認用パスワードは必須です")
    private String confirmPassword;

}
