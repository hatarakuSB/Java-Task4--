//DOMContentLoadedでHTMLの読込後、処理を開始。
document.addEventListener("DOMContentLoaded", function () {

    // form, input要素を取得
    const form = document.getElementById("loginForm");
    const username = document.querySelector("input[name='loUser']");
    const password = document.querySelector("input[name='loPass']");

    form.addEventListener("submit", function (e) {
        let errors = [];

        // ユーザー名チェック
        const usernamePattern = /^[a-zA-Z0-9]+$/;
        if (!username.value.trim()) {
            errors.push("ユーザー名が入力されていません");
        } else if (username.value.length > 8) {
            errors.push("ユーザー名は8文字以内で入力してください");
        } else if (!usernamePattern.test(username.value)) {
            errors.push("ユーザー名は半角英数字のみで入力してください");
        }

        // パスワードチェック
        const passwordPattern = /^[a-zA-Z0-9!@#$%^&*()_+\-=]*$/;
        if (!password.value.trim()) {
            errors.push("パスワードが入力されていません");
        } else if (password.value.length > 8) {
            errors.push("パスワードは8文字以内で入力してください");
        } else if (!passwordPattern.test(password.value)) {
            errors.push("パスワードに使用できない文字が含まれています");
        }

        // エラーがある場合は送信中止 & メッセージ表示
        if (errors.length > 0) {
            e.preventDefault();

            const errorBox = document.getElementById("error-message");
            if (errorBox) {
                // 箇条書きで表示
                const ul = document.createElement("ul");
                ul.classList.add("error-list");

                errors.forEach(msg => {
                    const li = document.createElement("li");
                    li.textContent = msg;
                    ul.appendChild(li);
                });

                // サーバーエラーを含め、既存内容を上書き
                errorBox.innerHTML = "";
                errorBox.appendChild(ul);
                errorBox.style.display = "block";
            }
        }
    });
});
