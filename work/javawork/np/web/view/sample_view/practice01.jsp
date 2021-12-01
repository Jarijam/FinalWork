<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function choice(){
		var size = document.getElementsByName("check").length;
		var text = "니가 고른 건 ";
		for(var i=0; i < size; i++){
			if(document.getElementsByName("check")[i].checked == true){
				text += (document.getElementsByName("check")[i].value);
				text += "&nbsp;&nbsp;";
			}
		}
		text += "입니다";
		document.getElementById("demo").innerHTML=text;
	}
</script>
</head>
<body>
	<h1>뭐 먹고 싶음?</h1>
	<input type='button' onclick='choice()' value='선택'><br>
	<input type='checkbox' name='check' value='짜장'>짜장<br>
	<input type='checkbox' name='check' value='부대찌개'>부대찌개<br>
	<input type='checkbox' name='check' value='국밥'>국밥<br>
	<input type='checkbox' name='check' value='삼육가'>삼육가<br><br>
	<div id="demo"></div>
</body>
</html>