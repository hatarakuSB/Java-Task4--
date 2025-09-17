document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("frmUserList");
    const errorBox = document.getElementById("error-message");

    form.addEventListener("submit", function (e) {
        const checked = form.querySelectorAll("input[name='deleteIds']:checked").length > 0;

        if (!checked) {
            e.preventDefault();
            showError("削除するユーザーを選択してください。");
            return;
        }

        if (!confirm("選択されたユーザーを本当に削除しますか？")) {
            e.preventDefault();
        }
    });

    function showError(message) {
        errorBox.textContent = message;
        errorBox.style.display = "block";
    }
});
