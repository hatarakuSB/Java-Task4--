package com.Shopping_Management.Model.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.LoginLogDAO;

import config.AppConstants;

/**
 * ログインログサービスクラス
 */
@Service
public class LoginLogService {

    private final LoginLogDAO loginLogDAO;

    public LoginLogService(LoginLogDAO loginLogDAO) {
        this.loginLogDAO = loginLogDAO;
    }

    /**
     * ログイン成功を記録
     *
     * @param userId Integer
     * @param loginUserName String 
     */
    public void logSuccess(Integer userId, String loginUserName) {
        loginLogDAO.insertLog(userId, loginUserName, AppConstants.LOG_EVENT_SUCCESS);
        writeCsv(AppConstants.LOG_EVENT_SUCCESS, userId, loginUserName, AppConstants.LOG_EVENT_NAME_SUCCESS);
    }

    /**
     * ログイン失敗を記録
     *
     * @param loginUserName String
     */
    public void logFailure(String loginUserName) {
        loginLogDAO.insertLog(null, loginUserName, AppConstants.LOG_EVENT_FAILURE);
        writeCsv(AppConstants.LOG_EVENT_FAILURE, null, loginUserName, AppConstants.LOG_EVENT_NAME_FAILURE);
    }

    /**
     * ログアウトを記録
     *
     * @param userId Integer
     * @param loginUserName String
     */
    public void logLogout(Integer userId, String loginUserName) {
        loginLogDAO.insertLog(userId, loginUserName, AppConstants.LOG_EVENT_LOGOUT);
        writeCsv(AppConstants.LOG_EVENT_LOGOUT, userId, loginUserName, AppConstants.LOG_EVENT_NAME_LOGOUT);
    }

    /**
     * CSVログファイルに追記
     *
     * @param eventCode String
     * @param userId Integer
     * @param loginUserName String
     * @param eventName String
     */
    private void writeCsv(String eventCode, Integer userId, String loginUserName, String eventName) {
        try {
            String date = new SimpleDateFormat(AppConstants.DATE_FORMAT_LOG_FILE).format(new Date());
            String fileName = AppConstants.LOG_DIR + "/login_log_" + date + ".csv";

            String timestamp = new SimpleDateFormat(AppConstants.DATE_FORMAT_LOG_TIMESTAMP).format(new Date());

            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(AppConstants.LOG_DIR));

            try (FileWriter fw = new FileWriter(fileName, true);
                 PrintWriter pw = new PrintWriter(fw)) {

                String line = String.format("%s,%s,%s,%s,%s",
                        timestamp,
                        eventCode,
                        userId == null ? "" : userId.toString(),
                        loginUserName,
                        eventName);

                pw.println(line);
                System.out.println("[LOGIN_LOG] " + line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
