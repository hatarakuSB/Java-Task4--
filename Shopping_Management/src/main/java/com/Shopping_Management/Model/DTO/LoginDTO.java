package com.Shopping_Management.Model.DTO;

import lombok.Data;

/**
 * ログイン情報DTOクラス
 */
@Data
public class LoginDTO {

    /** ユーザーID */
    private int userId;

    /** ユーザー名 */
    private String userName;

    /** パスワード */
    private String password;

    /** 権限 */
    private boolean authority;
}
