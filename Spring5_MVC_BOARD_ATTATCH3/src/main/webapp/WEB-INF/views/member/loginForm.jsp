<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
* {
	box-sizing: border-box;
}

@
keyframes view {
	from {transform: scale(0.1, 0.1)
}

to {
	transform: scale(1, 1)
}

}
form {
	animation: view 0.5s;
}

form {
	width: 500px;
	height: 350px;
	margin: 0 auto;
	border: 1px solid #fde9e9;
	padding: 10px;
}

.id, .passwd {
	width: 500px;
	height: 50px;
	border: 1px black #fde9e9;
	padding: 10px;
	text-indent: 10px;
}

#rm {
	font-weight: bold;
	padding: 5px 0;
}

.submitbtn {
	width: 50%;
	height: 50px;
	background: silver;
	color: white;
	font-size: 11pt;
	border-style: none;
	float: left;
}

.submitbtn:hover, .join:hover {
	opacity: 0.7;
}

.join {
	width: 50%;
	height: 50px;
	background: black;
	color: white;
	font-size: 11pt;
	border-style: none;
	float: left;
}

input {
	width: 100%;
	height: 30px;
	background: #fde9e9;
	border-style: none;
	margin: 10px 0;
}

.clearfix {
	margin-top: 30px;
}
</style>


</head>
<body>
	<h3>${saveid}</h3>
	<form name="loginform" method="post" action="loginProcess.net">
		<fieldset>
			<legend>
				<span></span> 로그인 <span></span>
			</legend>
			<label>ID</label> 
			<input type="text" size=10 name="id" id="id" placeholder="Enter id" required
				<c:if test="${!empty saveid}"> value=${saveid}</c:if>> <br>
			<label>비밀번호</label> <input type="password" name="password" id="pass">
			<div class="container">
				<input type="checkbox" name="remember"
					<c:if test="${!empty saveid}">checked</c:if> style="width: 30%; height: 20px; vertical-align: middle;">
					Remember me</div>
		
			<div class=clearfix>
				<button type="submit" class="submitbtn" value="로그인">로그인</button>
				<button type="button" class="join" value="취소">회원가입</button>
			</div>

		</fieldset>

	</form>
	
	
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-2.1.4.js"></script>
	<script>
		$(function() {
			$(".join").click(function() {

				location.href = "join.net";

			});

		});
	</script>
</body>

</html>