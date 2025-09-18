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
                    UserListDTO dto = new UserListDTO();
                    dto.setUserId(Integer.parseInt(cols[0]));
                    dto.setUserName(cols[1]);
                    dto.setPassword(cols[2]);

                    userListDAO.insert(dto); 
                }
            }
        }
    }

    /**
     * CSVエクスポート
     */
    public void exportUsersToCsv(HttpServletResponse response) throws IOException {
        List<UserListDTO> users = findAllUsers(); // ユーザー一覧を取得

        // CSVダウンロード用のレスポンス設定
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"user_list.csv\"");

        // 出力ストリーム取得
        PrintWriter writer = response.getWriter();

        // ヘッダー行
        writer.println("USER_ID,USER_NAME,PASSWORD");

        // データ行
        for (UserListDTO user : users) {
            writer.printf("%d,%s,%s%n",
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword());
        }

        writer.flush();
    }

}
