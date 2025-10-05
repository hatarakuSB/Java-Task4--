document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
    const messageBox = document.getElementById("messageBox");

    const username = document.querySelector("input[name='loUser']");
    const password = document.querySelector("input[name='loPass']");

    form.addEventListener("submit", function (e) {
        let errors = [];

        const usernamePattern = /^[a-zA-Z0-9]+$/;
        if (!username.value.trim()) {
            errors.push("ユーザー名が入力されていません");
        } else if (username.value.length > 8) {
            errors.push("ユーザー名は8文字以内で入力してください");
        } else if (!usernamePattern.test(username.value)) {
            errors.push("ユーザー名は半角英数字のみで入力してください");
        }

        const passwordPattern = /^[a-zA-Z0-9!@#$%^&*()_+\-=]*$/;
        if (!password.value.trim()) {
            errors.push("パスワードが入力されていません");
        } else if (password.value.length > 8) {
            errors.push("パスワードは8文字以内で入力してください");
        } else if (!passwordPattern.test(password.value)) {
            errors.push("パスワードに使用できない文字が含まれています");
        }

        if (errors.length > 0) {
            e.preventDefault();
            messageBox.style.display = "block";
            messageBox.className = "message-box error-box"; 
            messageBox.innerHTML = errors.join("<br>");
        }
    });
});
