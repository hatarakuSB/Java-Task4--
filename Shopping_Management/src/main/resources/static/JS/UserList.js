document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("frmUserList");
    const messageBox = document.getElementById("messageBox");

    // 削除チェック処理
    form.addEventListener("submit", function (e) {
        const checkboxes = form.querySelectorAll("input[name='deleteIds']");
        let checked = false;

        checkboxes.forEach(cb => {
            if (cb.checked) {
                checked = true;
            }
        });

        if (!checked) {
            e.preventDefault();
            messageBox.style.display = "block";
            messageBox.className = "message-box error-box";
            messageBox.textContent = "削除するユーザーを選択してください。";
        } else {
            if (!confirm("選択されたユーザーを本当に削除しますか？")) {
                e.preventDefault();
            }
        }
    });

    // CSVインポート処理
    const fileInput = document.getElementById("fileInput");
    const btnCsvImport = document.getElementById("btnCsvImport");
    const csvImportForm = document.getElementById("csvImportForm");

    btnCsvImport.addEventListener("click", function () {
        fileInput.click();
    });

    fileInput.addEventListener("change", function () {
        if (fileInput.files.length > 0) {
            if (confirm("選択したファイルをインポートしますか？")) {
                csvImportForm.submit();
            }
        }
    });
});
