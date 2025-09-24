document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("registerForm");
    const messageBox = document.getElementById("messageBox");

    const categoryId = document.querySelector("input[name='categoryId']");
    const productId = document.querySelector("input[name='productId']");
    const amount = document.querySelector("input[name='amount']");
    const place = document.querySelector("input[name='place']");
    const buyDate = document.querySelector("input[name='buyDate']");

    form.addEventListener("submit", function (e) {
        let errors = [];

        if (!categoryId.value.trim()) {
            errors.push("カテゴリを入力してください。");
        }
        if (!productId.value.trim()) {
            errors.push("商品名を入力してください。");
        }
        if (!amount.value.trim()) {
            errors.push("金額を入力してください。");
        } else if (!/^[0-9]+$/.test(amount.value)) {
            errors.push("金額は数字で入力してください。");
        } else if (amount.value.length > 6) {
            errors.push("金額は6桁以内で入力してください。");
        }
        if (!place.value.trim()) {
            errors.push("場所を入力してください。");
        } else if (place.value.length > 20) {
            errors.push("場所は20文字以内で入力してください。");
        }
        if (!buyDate.value.trim()) {
            errors.push("日付を入力してください。");
        } else {
            const datePattern = /^\d{4}-\d{2}-\d{2}$/;
            const value = buyDate.value.split("T")[0];
            if (!datePattern.test(value)) {
                errors.push("日付は YYYY-MM-DD 形式で入力してください。");
            }
        }

        if (errors.length > 0) {
            e.preventDefault();
            messageBox.style.display = "block";
            messageBox.className = "message-box error-box";
            messageBox.innerHTML = errors.join("<br>");
        }
    });
});
