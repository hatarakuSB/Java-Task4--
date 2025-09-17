document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("inventoryForm");
    const errorBox = document.getElementById("error-message");

    form.addEventListener("submit", function (e) {
        const checkboxes = form.querySelectorAll("input[type='checkbox']");
        let checked = false;

        checkboxes.forEach(cb => {
            if (cb.checked) {
                checked = true;
            }
        });

        if (!checked) {
            e.preventDefault(); // フォーム送信を止める
            errorBox.style.display = "block";
            errorBox.textContent = "削除する商品を選択してください。";
        } else {
            errorBox.style.display = "none";
        }
    });
});
