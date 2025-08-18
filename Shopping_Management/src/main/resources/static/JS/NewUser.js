document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("userForm");
    const errorBox = document.getElementById("errorBox");

    const username = document.querySelector("[name='username']");
    const password = document.querySelector("[name='password']");
    const confirmPassword = document.querySelector("[name='confirmPassword']");

    const alnumRegex = /^[a-zA-Z0-9]+$/;

    form.addEventListener("submit", function(event) {
        let messages = [];

        // ユーザー名
        if (!username.value.trim()) {
            messages.push("ユーザー名は必須です");
        } else if (username.value.length > 20) {
            messages.push("ユーザー名は20文字以内で入力してください");
        } else if (!alnumRegex.test(username.value)) {
            messages.push("ユーザー名は半角英数字のみ使用できます");
        }

        // パスワード
        if (!password.value.trim()) {
            messages.push("パスワードは必須です");
        } else if (password.value.length < 6 || password.value.length > 8) {
            messages.push("パスワードは6～8文字で入力してください");
        } else if (!alnumRegex.test(password.value)) {
            messages.push("パスワードは半角英数字のみ使用できます");
        }

        // 確認用パスワード
        if (!confirmPassword.value.trim()) {
            messages.push("確認用パスワードは必須です");
        } else if (password.value !== confirmPassword.value) {
            messages.push("確認用パスワードが一致しません");
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
