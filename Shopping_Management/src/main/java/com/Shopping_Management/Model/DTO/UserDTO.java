package com.Shopping_Management.Model.DTO;

import lombok.Data;

@Data
public class UserDTO {

//	@NotBlank(message = "ユーザー名は必須です")
//    @Size(max = 8, message = "ユーザー名は8文字以内で入力してください")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ユーザー名は半角英数字のみ使用できます")
    private String username;

//    @NotBlank(message = "パスワードは必須です")
//    @Size(max = 8, message = "8文字以内で入力してください")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "パスワードは半角英数字のみ使用できます")
    private String password;

//    @NotBlank(message = "確認用パスワードは必須です")
    private String confirmPassword;

}
