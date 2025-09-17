document.addEventListener("DOMContentLoaded", function () {
	const form = document.getElementById("registerForm");
    const categoryId = document.querySelector("input[name='categoryId']");
    const productId = document.querySelector("input[name='productId']");
    const amount = document.querySelector("input[name='amount']");
    const place = document.querySelector("input[name='place']");
    const buyDate = document.querySelector("input[name='buyDate']");
    const errorBox = document.getElementById("error-message");

    form.addEventListener("submit", function (e) {
        let errors = [];

        // カテゴリ
        if (!categoryId.value.trim()) {
            errors.push("カテゴリを入力してください。");
        }

        // 商品名
        if (!productId.value.trim()) {
            errors.push("商品名を入力してください。");
        }

        // 金額
        if (!amount.value.trim()) {
            errors.push("金額を入力してください。");
        } else if (!/^[0-9]+$/.test(amount.value)) {
            errors.push("金額は数字で入力してください。");
        } else if (amount.value.length > 6) {
            errors.push("金額は6桁以内で入力してください。");
        }

        // 場所
        if (!place.value.trim()) {
            errors.push("場所を入力してください。");
        } else if (place.value.length > 20) {
            errors.push("場所は20文字以内で入力してください。");
        }

        // 日付
        if (!buyDate.value.trim()) {
            errors.push("日付を入力してください。");
        } else {
            const datePattern = /^\d{4}-\d{2}-\d{2}$/;
            const value = buyDate.value.split("T")[0]; // datetime-local対応
            if (!datePattern.test(value)) {
                errors.push("日付は YYYY/MM/DD 形式で入力してください。");
            }
        }

        // エラーがあれば表示
        if (errors.length > 0) {
            e.preventDefault();

            errorBox.style.display = "block";
            errorBox.innerHTML = errors.map(msg => `<span>${msg}</span><br>`).join("");
        } else {
            errorBox.style.display = "none";
            errorBox.innerHTML = "";
        }
    });
});
