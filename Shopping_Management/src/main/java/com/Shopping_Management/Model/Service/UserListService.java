package com.Shopping_Management.Model.Service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Shopping_Management.Model.DAO.UserListDAO;
import com.Shopping_Management.Model.DTO.UserListDTO;

@Service
public class UserListService {

    private final UserListDAO userListDAO;

    public UserListService(UserListDAO userListDAO) {
        this.userListDAO = userListDAO;
    }

    /**
     * ユーザー一覧取得
     */
    public List<UserListDTO> findAllUsers() {
        return userListDAO.findAll();
    }

    /**
     * 複数ユーザー削除（DAOでトランザクション実行）
     */
    public void deleteUsersWithDetails(List<Integer> userIds) {
        for (Integer userId : userIds) {
        	userListDAO.deleteUserWithDetails(userId);
        }
    }

    /**
     * CSVインポート
     */
    public void importUsersFromCsv(MultipartFile file) throws IOException {
        // TODO: CSV読み込み処理
    }

    /**
     * CSVエクスポート
     */
    public void exportUsersToCsv(HttpServletResponse response) throws IOException {
        // TODO: CSV出力処理
    }
}
