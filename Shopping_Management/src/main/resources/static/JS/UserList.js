document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("frmUserList");
    const errorBox = document.getElementById("error-message");

    form.addEventListener("submit", function (e) {
        const checkboxes = form.querySelectorAll("input[name='deleteIds']");
        let checked = false;

        checkboxes.forEach(cb => {
            if (cb.checked) {
                checked = true;
            }
        });

        if (!checked) {
            e.preventDefault(); // フォーム送信を止める
            errorBox.style.display = "block";
            errorBox.textContent = "削除するユーザーを選択してください。";
        } else {
            errorBox.style.display = "none";

            if (!confirm("選択されたユーザーを本当に削除しますか？")) {
                e.preventDefault();
            }
        }
    });
});
