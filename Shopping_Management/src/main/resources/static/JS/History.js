document.addEventListener("DOMContentLoaded", function () {
    flatpickr("#dateFrom", { dateFormat: "Y/m/d", allowInput: true });
    flatpickr("#dateTo", { dateFormat: "Y/m/d", allowInput: true });

    const form = document.getElementById("historyForm");
    const messageBox = document.getElementById("messageBox");

    form.addEventListener("submit", function (e) {
        let errors = [];

        const moneyFrom = document.getElementById("moneyFrom").value.trim();
        const moneyTo = document.getElementById("moneyTo").value.trim();
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

        const dateFrom = document.getElementById("dateFrom").value.trim();
        const dateTo = document.getElementById("dateTo").value.trim();
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

        if (errors.length > 0) {
            e.preventDefault();
            messageBox.style.display = "block";
            messageBox.className = "message-box error-box";
            messageBox.innerHTML = errors.join("<br>");
        }
    });
});
