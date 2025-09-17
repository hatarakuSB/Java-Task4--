package com.Shopping_Management.Model.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.LoginLogDAO;

@Service
public class LoginLogService {

    private final LoginLogDAO loginLogDAO;

    // ログ保存ディレクトリ
    private static final String LOG_DIR = "logs";

    public LoginLogService(LoginLogDAO loginLogDAO) {
        this.loginLogDAO = loginLogDAO;
    }

    /**
     * ログイン成功
     */
    public void logSuccess(Integer userId, String loginUserName) {
        // DBに保存
        loginLogDAO.insertLog(userId, loginUserName, "01");
        // CSVに保存
        writeCsv("01", userId, loginUserName, "成功");
    }

    /**
     * ログイン失敗
     */
    public void logFailure(String loginUserName) {
        loginLogDAO.insertLog(null, loginUserName, "02");
        writeCsv("02", null, loginUserName, "失敗");
    }

    /**
     * ログアウト
     */
    public void logLogout(Integer userId, String loginUserName) {
        loginLogDAO.insertLog(userId, loginUserName, "03");
        writeCsv("03", userId, loginUserName, "ログアウト");
    }

    /**
     * CSV出力（日ごとにファイルを分ける）
     */
    private void writeCsv(String eventCode, Integer userId, String loginUserName, String eventName) {
        try {
            // 日付でファイル名を分ける
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String fileName = LOG_DIR + "/login_log_" + date + ".csv";

            // 時刻
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // ディレクトリ作成（存在しなければ）
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(LOG_DIR));

            // 追記モードで書き込み
            try (FileWriter fw = new FileWriter(fileName, true);
                 PrintWriter pw = new PrintWriter(fw)) {

            	String line = String.format("%s,%s,%s,%s,%s",
                        timestamp,
                        eventCode,
                        userId == null ? "" : userId.toString(),
                        loginUserName,
                        eventName);

                // ファイルに出力
                pw.println(line);

                // コンソールにも出力
                System.out.println("[LOGIN_LOG] " + line); 
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
