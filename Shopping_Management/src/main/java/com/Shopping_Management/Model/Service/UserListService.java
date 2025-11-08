package com.Shopping_Management.Model.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Shopping_Management.Model.DAO.UserListDAO;
import com.Shopping_Management.Model.DTO.UserListDTO;

import config.AppConstants;

/**
 * ユーザー一覧サービスクラス
 */
@Service
public class UserListService {

    private final UserListDAO userListDAO;

    public UserListService(UserListDAO userListDAO) {
        this.userListDAO = userListDAO;
    }

    /**
     * ユーザー一覧を取得
     *
     * @return List<UserListDTO> ユーザーリスト
     */
    public List<UserListDTO> findAllUsers() {
        return userListDAO.findAll();
    }

    /**
     * 複数ユーザーを削除
     *
     * @param userIds List<Integer> 削除対象のユーザーIDリスト
     */
    public void deleteUsersWithDetails(List<Integer> userIds) {
        for (Integer userId : userIds) {
            userListDAO.deleteUserWithDetails(userId);
        }
    }

    /**
     * CSVファイルからユーザーをインポート
     */
    public String importUsersFromCsv(MultipartFile file) throws IOException {
        StringBuilder errorMessage = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] cols = line.split(",");
                if (cols.length >= 3) {
                    int userId = Integer.parseInt(cols[0]);
                    String userName = cols[1];
                    String password = cols[2];

                    // 重複チェック
                    if (userListDAO.exists(userId, userName)) {
                        // どちらが重複しているか個別に確認
                        if (userListDAO.exists(userId, "")) {
                            errorMessage.append(userId).append("は既にあるユーザーIDです。").append("<br>");
                        }
                        if (userListDAO.exists(0, userName)) {
                        	errorMessage.append(userName).append("は既にあるユーザーネームです。").append("<br>");
                        }
                        continue;
                    }

                    // 登録
                    UserListDTO dto = new UserListDTO();
                    dto.setUserId(userId);
                    dto.setUserName(userName);
                    dto.setPassword(password);
                    userListDAO.insert(dto);
                }
            }
        }

        if (errorMessage.length() > 0) {
            return errorMessage.toString();
        }
        return null;
    }

    /**
     * ユーザー一覧をCSVとしてエクスポート
     *
     * @param response HttpServletResponse CSV出力先レスポンス
     * @throws IOException 入出力例外
     */
    public void exportUsersToCsv(HttpServletResponse response) throws IOException {
        List<UserListDTO> users = findAllUsers();

        response.setContentType(AppConstants.CSV_CONTENT_TYPE);
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + AppConstants.CSV_USER_LIST_FILENAME + "\"");

        PrintWriter writer = response.getWriter();
        writer.println(AppConstants.CSV_USER_LIST_HEADER);

        for (UserListDTO user : users) {
            writer.printf("%d,%s,%s%n",
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword());
        }

        writer.flush();
    }
}
