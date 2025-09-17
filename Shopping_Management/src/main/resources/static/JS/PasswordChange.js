document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("passwordForm");
    const errorBox = document.getElementById("errorBox");

    const currentPassword = document.querySelector("[name='currentPassword']");
    const newPassword = document.querySelector("[name='newPassword']");
    const confirmPassword = document.querySelector("[name='confirmPassword']");

    const alnumRegex = /^[a-zA-Z0-9]+$/;

    form.addEventListener("submit", function(event) {
        let messages = [];

        // 現在のパスワード
        if (!currentPassword.value.trim()) {
            messages.push("現在のパスワードは必須です");
        } else if (currentPassword.value.length !== 8) {
            messages.push("現在のパスワードは8文字で入力してください");
        } else if (!alnumRegex.test(currentPassword.value)) {
            messages.push("現在のパスワードは半角英数字のみ使用できます");
        }

        // 新規のパスワード
        if (!newPassword.value.trim()) {
            messages.push("新規のパスワードは必須です");
        } else if (newPassword.value.length !== 8) {
            messages.push("新規のパスワードは8文字で入力してください");
        } else if (!alnumRegex.test(newPassword.value)) {
            messages.push("新規のパスワードは半角英数字のみ使用できます");
        }

        // 確認用パスワード
        if (!confirmPassword.value.trim()) {
            messages.push("確認用パスワードは必須です");
        } else if (confirmPassword.value.length !== 8) {
            messages.push("確認用パスワードは8文字で入力してください");
        } else if (!alnumRegex.test(confirmPassword.value)) {
            messages.push("確認用パスワードは半角英数字のみ使用できます");
        }

        // エラーがあれば表示して送信を止める
        if (messages.length > 0) {
            event.preventDefault();
            errorBox.style.display = "block";
            errorBox.innerHTML = messages.join("<br>");
        } else {
            errorBox.style.display = "none";
        }
    });
});
