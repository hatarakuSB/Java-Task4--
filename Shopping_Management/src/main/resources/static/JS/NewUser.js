document.addEventListener("DOMContentLoaded", function() {
	const form = document.getElementById("userForm");
	const messageBox = document.getElementById("messageBox");
	const username = document.getElementById("username");
	const password = document.getElementById("password");
	const confirmPassword = document.getElementById("confirmPassword");

	const alnumRegex = /^[a-zA-Z0-9]+$/;

	form.addEventListener("submit", function(event) {
		let messages = [];

		if (!username.value.trim()) {
			messages.push("ユーザー名は必須です");
		} else if (username.value.length > 20) {
			messages.push("ユーザー名は20文字以内で入力してください");
		} else if (!alnumRegex.test(username.value)) {
			messages.push("ユーザー名は半角英数字のみ使用できます");
		}

		if (!password.value.trim()) {
			messages.push("パスワードは必須です");
		} else if (password.value.length < 6 || password.value.length > 8) {
			messages.push("パスワードは6～8文字で入力してください");
		} else if (!alnumRegex.test(password.value)) {
			messages.push("パスワードは半角英数字のみ使用できます");
		}

		if (!confirmPassword.value.trim()) {
			messages.push("確認用パスワードは必須です");
		} else if (confirmPassword.value.length < 6 || confirmPassword.value.length > 8) {
			messages.push("パスワードは6～8文字で入力してください");
		} else if (!alnumRegex.test(confirmPassword.value)) {
			messages.push("パスワードは半角英数字のみ使用できます");
		} else if (password.value != confirmPassword.value) {
			messages.push("確認用パスワードが一致しません");
		}


		if (messages.length > 0) {
			event.preventDefault();
			messageBox.style.display = "block";
			messageBox.className = "message-box error-box";
			messageBox.innerHTML = messages.join("<br>");
		}
	});
});
