document.addEventListener("DOMContentLoaded", function () {
    // flatpickr 初期化
    flatpickr("#dateFrom", {
        dateFormat: "Y/m/d", 
        allowInput: true 
    });

    flatpickr("#dateTo", {
        dateFormat: "Y/m/d",
        allowInput: true
    });

    // フォームバリデーション
    const form = document.getElementById("historyForm");
    const errorBox = document.getElementById("errorBox");

    form.addEventListener("submit", function (e) {
        let errors = [];

        // 商品名（hidden保持 → 未選択ならチェック）
        const productId = document.querySelector("input[name='productId']").value;
        if (!productId) {
            errors.push("商品名を選択してください");
        }

        // 金額チェック
        const moneyFrom = document.querySelector("input[name='moneyFrom']").value.trim();
        const moneyTo = document.querySelector("input[name='moneyTo']").value.trim();
        const moneyPattern = /^[0-9]*$/;

        if (!moneyFrom && moneyTo) {
            errors.push("金額（下限）を入力してください");
        }

        if (moneyFrom && !moneyPattern.test(moneyFrom)) {
            errors.push("金額（下限）は数字で入力してください");
        } else if (moneyFrom && moneyFrom.length > 6) {
            errors.push("金額（下限）は6桁以内で入力してください");
        }

        if (moneyTo && !moneyPattern.test(moneyTo)) {
            errors.push("金額（上限）は数字で入力してください");
        } else if (moneyTo && moneyTo.length > 6) {
            errors.push("金額（上限）は6桁以内で入力してください");
        }

        // 日付チェック
        const dateFrom = document.querySelector("#dateFrom").value.trim();
        const dateTo = document.querySelector("#dateTo").value.trim();
        const datePattern = /^\d{4}\/\d{2}\/\d{2}$/; 

        if (!dateFrom && dateTo) {
            errors.push("日付（開始日）を入力してください");
        }

        if (dateFrom && !datePattern.test(dateFrom)) {
            errors.push("日付（開始日）は yyyy/MM/dd 形式で入力してください");
        }

        if (dateTo && !datePattern.test(dateTo)) {
            errors.push("日付（終了日）は yyyy/MM/dd 形式で入力してください");
        }

        
        // エラーメッセージ表示
        if (errors.length > 0) {
            e.preventDefault();
            errorBox.style.display = "block";
            errorBox.innerHTML = errors.join("<br>");
        } else {
            errorBox.style.display = "none";
        }
    });
});
