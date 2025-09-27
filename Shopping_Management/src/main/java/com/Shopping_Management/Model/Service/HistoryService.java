package com.Shopping_Management.Model.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.HistoryDAO;
import com.Shopping_Management.Model.DTO.HistoryDTO;

import config.AppConstants;
import parts.HistoryForm;

/**
 * 履歴検索サービスクラス
 */
@Service
public class HistoryService {
    private final HistoryDAO historyDAO;

    public HistoryService(HistoryDAO historyDAO) {
        this.historyDAO = historyDAO;
    }

    /**
     * 入力条件を検証
     *
     * @param form HistoryForm
     * @return List String エラーメッセージ一覧（正常時は空リスト）
     */
    public List<String> validateConditions(HistoryForm form) {
        List<String> errors = new ArrayList<>();

        // 金額の相関チェック
        if (form.getMoneyFrom() != null && form.getMoneyTo() != null) {
            if (form.getMoneyFrom() > form.getMoneyTo()) {
                errors.add(AppConstants.MSG_HISTORY_INVALID_MONEY_RANGE);
            }
        }

        // 日付の相関チェック
        if (form.getDateFrom() != null && !form.getDateFrom().isEmpty()
                && form.getDateTo() != null && !form.getDateTo().isEmpty()) {
            try {
                LocalDate from = LocalDate.parse(form.getDateFrom(),
                        DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT));
                LocalDate to = LocalDate.parse(form.getDateTo(),
                        DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT));
                if (from.isAfter(to)) {
                    errors.add(AppConstants.MSG_HISTORY_INVALID_DATE_RANGE);
                }
            } catch (DateTimeParseException e) {
                errors.add(AppConstants.MSG_HISTORY_INVALID_DATE_FORMAT);
            }
        }

        return errors;
    }

    /**
     * 条件を指定して履歴を検索
     *
     * @param form HistoryForm
     * @param userId int
     * @return List HistoryDTO
     */
    public List<HistoryDTO> search(HistoryForm form, int userId) {
        return historyDAO.searchByConditions(form, userId);
    }

    /**
     * 検索結果が空のメッセージ判定
     *
     * @param results List HistoryDTO
     * @return String データなしの場合はメッセージ、ありの場合は null
     */
    public String checkNoData(List<HistoryDTO> results) {
        if (results == null || results.isEmpty()) {
            return AppConstants.MSG_NO_DATA;
        }
        return null;
    }
}
