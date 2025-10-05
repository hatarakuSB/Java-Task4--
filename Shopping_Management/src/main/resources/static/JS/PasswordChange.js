document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("passwordChangeForm");
    const messageBox = document.getElementById("messageBox");

    const currentPassword = document.querySelector("input[name='currentPassword']");
    const newPassword = document.querySelector("input[name='newPassword']");
    const confirmPassword = document.querySelector("input[name='confirmPassword']");

    const alnumRegex = /^[a-zA-Z0-9]+$/;

    form.addEventListener("submit", function (e) {
        let errors = [];

        if (!currentPassword.value.trim()) {
            errors.push("現在のパスワードを入力してください");
        }

        if (!newPassword.value.trim()) {
            errors.push("新しいパスワードを入力してください");
        } else if (newPassword.value.length < 6 || newPassword.value.length > 8) {
            errors.push("新しいパスワードは6～8文字で入力してください");
        } else if (!alnumRegex.test(newPassword.value)) {
            errors.push("新しいパスワードは半角英数字のみ使用できます");
        }

        if (!confirmPassword.value.trim()) {
            errors.push("確認用パスワードを入力してください");
        } else if (newPassword.value !== confirmPassword.value) {
            errors.push("新しいパスワードと確認用パスワードが一致しません");
        }

        if (errors.length > 0) {
            e.preventDefault();
            messageBox.style.display = "block";
            messageBox.className = "message-box error-box";
            messageBox.innerHTML = errors.join("<br>");
        }
    });
});
