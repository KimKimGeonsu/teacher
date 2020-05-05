<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
@charset "UTF-8";

legend {
	font-size: 17pt;
	font-weight: bold;
	text-align: center;
	color: gray;
}

fieldset {
	margin: 0 auto;
	border: 1px solid silver;
	border-radius: 30px;
	padding: 30px;
}

form {
	width: 530px;
	background: white;
	margin: 0 auto;
	margin-top: 30px;
}

label {
	display: inline-block;
	font-weight: bold;
	margin: 23px 0 5px 0;
	font-size: 11pt;
}

input {
	outline: none;
}

input[type=text], input[name=pass] {
	text-indent: 7px;
	border: none;
	border-bottom: 1px solid silver;
	height: 30px;
	width: 99%;
}

input[type=button] {
	display: inline-block;
	height: 32px;
	width: 19%;
	background: #ff9528;
	color: white;
	border-style: none;
	line-height: 32px;
	font-size: 9pt;
	cursor: pointer;
}

input[type=button]:hover {
	opacity: 0.7;
	border-radius: 15px;
	transition: 0.3s;
}

input:focus {
	background: #29d6ce;
	border-bottom: 1px solid #29d6ce;
	opacity: 0.7;
	transition: 1s;
}

#sel {
	height: 30px;
	width: 19.9%;
	background: #dddddd;
	outline: none;
	font-size: 11px;
	border: 1px solid #eaeaea;
	margin-left: 0.5px
}

#gender {
	height: 35px;
	width: 100%;
	border: 1px solid #eaeaea;
	line-height: 35px;
	margin-bottom: 10px;
}

button {
	font-size: 10.5pt;
}

button[type='submit'] {
	background: #29d6ce;
	color: white;
	margin-top: 37px;
}

button {
	height: 35px;
	width: 49%;
	border-style: none;
	cursor: pointer;
}

button:hover {
	opacity: 0.7;
	border-radius: 15px;
	transition: 0.3s;
}

#gender:focus {
	background: #29d6ce;
	transition: 1s;
}

span {
	color: red;
	font-size: 11px;
	position: absolute;
}

input::placeholder {
	font-size: 14px;
	color: #a2a2a2
}

#gender, #month {
	font-size: 14px;
}

#message {
	font-size: 13px;
}
</style>
<script>
	$(document).ready(
			function() {

				var chidkid = false;
				var chidkemail = false;

				$('#id').keyup(function() {

					//	var req = /[a-zA-Z0-9_]{5,12}/;
					var req = /^\w{5,12}$/;
					$('#message').empty();
					if (!req.test($('#id').val())) {
						$('#message').text("형식에 맞게 아이디를 작성하셈");
						$('#message').css('color', 'red');

					} else {

						/*ajax 경로 지정 경로문제 경로
						꼭 jsp 단에서 시작해야함
						
						  만약에 index.jsp 에서 시작하면
						  http://localhost:8088/Ajax_Servlet/index.jsp 가 기준경로가 됨
						 
						  그러니까 
						  
						  url : 'join' 이면 
						  http://localhost:8088/Ajax_Servlet/join 으로 가짐
						  
						  url : '../join' 이면 
						  http://localhost:8088/join 으로 가짐
						  
						 */
						$.ajax({

							url : 'idcheck.net',
							data : {
								"id" : $('#id').val()
							},
							dataType : 'json',
							success : function(rdata) {

								if (rdata != -1) {
									$('#message').text("이미 사용중인 아이디");
									$('#message').css('color', 'red');
									chidkid = false;
								} else {
									$('#message').text("사용 가능한 아이디");
									$('#message').css('color', 'blue');
									chidkid = true;
								}

							},
							error : function() {
								//alert('에러');
							},
							complete : function() {
								//alert('완료');
							}

						});

					}
				});

				$('#email').on(
						'keyup',
						function() {

							$('#email_message').empty();

							var pattern = /\w+@\w+[.]\w{3}/;
							var email = $("#email").val();
							console.log(email);
							console.log(!pattern.test(email));

							if (!pattern.test(email)) {
								$("#email_message").css('color', 'red').html(
										"이메일 형식이 맞지 않습니다.");
								chidkemail = false;
							} else {
								$("#email_message").css('color', 'green').html(
										'이메일 형식에 맞습니다.');
								chidkemail = true;
							}
						});

				$('form').submit(function() {

					if (!chidkid) {
						alert("아디 제대로됬는지 확인ㄱㄱ");
						$('input:eq(0)').val('').focus();
						$('#message').text('');
						return false;
					}

					if (!chidkemail) {
						alert("이메일 제대로됬는지 확인ㄱㄱ");
						$('#email').focus();
						return false;
					}

					if (!$.isNumeric($("input[name='age']").val())) {
						alert("나이는 숫자로 입력하세요.");
						$("input[name='age']").val('');
						$("input[name='age']").focus();
						return false;
					}

				});

			});
</script>
</head>
<body>
	<form method="post" action="joinProcess.net">
		<fieldset>

			<legend>유효성 검사</legend>
			<label>ID</label> <input type="text" maxlength="12" name="id" id="id">
			<span id="message"></span> <br> <label>비밀번호</label> <input
				type="password" name="password" id="pass" required><br> <label>이름</label>
			<input type="text" maxlength="15" name="name" id="name" required><br>

			<label>나이</label> <input type="text" maxlength="12" name="age"
				id="age"> <br> <label>성별</label>

			<div>
				<input type="radio" name="gender" value="남" checked><span>남자</span>
				<input type="radio" name="gender" value="여" checked><span>여자</span>
			</div>

			<b>이메일주소</b> <input type="email" name="email"
				placeholder="Enter email" id="email" required><span
				id="email_message"></span>
			<div class="clearfix">
				<button type="submit" class="submitbtn">회원가입</button>
				<button type="reset" class="cancelbtn">다시작성</button>
			</div>

		</fieldset>
	</form>




</body>
</html>