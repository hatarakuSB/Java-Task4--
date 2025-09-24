document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("inventoryForm");
    const messageBox = document.getElementById("messageBox");

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
            messageBox.style.display = "block";
            messageBox.className = "message-box error-box"; // ★ error 表示
            messageBox.textContent = "削除する商品を選択してください。";
        }
    });
});
