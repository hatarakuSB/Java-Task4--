package com.Shopping_Management.Model.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DAO.HistoryDAO;
import com.Shopping_Management.Model.DTO.HistoryDTO;

import parts.HistoryForm;
@Service
public class HistoryService {
	private final HistoryDAO historyDAO;

	public HistoryService(HistoryDAO historyDAO) {
		this.historyDAO = historyDAO;
	}

	/**
	 * 履歴検索処理
	 * @param form 検索条件
	 * @param userId ログインユーザーID
	 * @return エラーメッセージ（成功時は null）
	 */
	public List<String> validateConditions(HistoryForm form) {
	    List<String> errors = new ArrayList<>();

	    // 金額相関チェック
	    if (form.getMoneyFrom() != null && form.getMoneyTo() != null) {
	        if (form.getMoneyFrom() > form.getMoneyTo()) {
	            errors.add("金額（下限）は金額（上限）以下を入力してください。");
	        }
	    }

	    // 日付相関チェック
	    if (form.getDateFrom() != null && !form.getDateFrom().isEmpty()
	            && form.getDateTo() != null && !form.getDateTo().isEmpty()) {
	        try {
	            LocalDate from = LocalDate.parse(form.getDateFrom(),
	                    DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	            LocalDate to = LocalDate.parse(form.getDateTo(),
	                    DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	            if (from.isAfter(to)) {
	                errors.add("日付（開始日）は日付（終了日）以前を入力してください。");
	            }
	        } catch (DateTimeParseException e) {
	            errors.add("日付の形式が不正です。（yyyy/MM/dd 形式で入力してください）");
	        }
	    }

	    return errors;
	}

	/**
	 * 検索実行
	 * @param form 検索条件
	 * @param userId ユーザーID
	 * @return 履歴リスト
	 */
	public List<HistoryDTO> search(HistoryForm form, int userId) {
		return historyDAO.searchByConditions(form, userId);
	}
	
	 /**
     * 検索結果が空の場合のメッセージ判定
     * @param results 検索結果リスト
     * @return エラーメッセージ（データなしならメッセージ、ありなら null）
     */
    public String checkNoData(List<HistoryDTO> results) {
        if (results == null || results.isEmpty()) {
            return "該当するデータはありません。";
        }
        return null;
    }
}
