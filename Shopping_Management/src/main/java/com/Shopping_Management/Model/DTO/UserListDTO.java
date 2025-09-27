package com.Shopping_Management.Model.DTO;

import lombok.Data;

/**
 * ユーザー一覧DTOクラス
 */
@Data
public class UserListDTO {

    /** ユーザーID */
    private int userId;

    /** ユーザー名 */
    private String userName;

    /** パスワード */
    private String password;
}
