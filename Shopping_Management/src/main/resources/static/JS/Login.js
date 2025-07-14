/**
 * 
 */
function check(){
	
	let ErrorMsg = "";
	
	if(document.getElementById("Username").value == ""){
		ErrorMsg= "必須項目です。入力してください。";
		document.getElementById("validate_msg").innerHTML= ErrorMsg
		return ErrorMsg;
	}
	if(document.getElementById("Password").value == ""){
		ErrorMsg= "必須項目です。入力してください。";
		ocument.getElementById("validate_msg").innerHTML= ErrorMsg
		return ErrorMsg;
	}
	alert("入力内容に誤りがあります。")
	
	return ErrorMsg;
}