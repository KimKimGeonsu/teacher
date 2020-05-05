<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원관리 시스템 회원수정 페이지</title>
<link href="resources/css/loginForm.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/updateForm.js"></script>
<style>
	input{background:#efefef;}
	button[type=submit]{background:black; color:white;}
	button[type=button]{background:white; color:black;}
	form{border:none;}
	legend{font-size:20px; text-align:center; margin-bottom:15px!important;}
	input[type=radio]{width:15px; height:13px;}
	input{margin-bottom:15px!important;}
	.out_container{width:530px; height:630px; border:1px solid silver; margin:0 auto; border-radius:15px;}
	input[name=email]{margin-bottom:0px!important;}
</style>
<script>
	$(function(){
		var pandan = '${member.gender}';
		if(pandan == '남'){
			$("input:radio").eq(0).attr('checked','checked');
		}else{
			$("input:radio").eq(1).attr('checked','checked');
		}
	})
</script>
</head>
<body>	
<jsp:include page="/header.jsp"/>
<div class="out_container">
	<form name="joinform" action="updateProcess.net" method="post">
   	<fieldset>
      <legend>회원정보 수정 페이지</legend>
      	 <label><b>아이디</b></label>
         <input type=text id="id" name=id value="${member.id}" maxLength=12 autocomplete="off" readOnly>
         <span id="message"></span><br>
         
         <label><b>비밀번호</b></label>
         <input type=password id="pass" name="password" value="${member.password}" required><br>
         
         <label><b>이름</b></label>
         <input type=text name=name maxlength=15 value="${member.name}" readOnly>
         
         <label><b>나이</b></label>
         <input type=text name=age maxlength=2 value="${member.age}">
         
         <label><b>성별</b></label><br>
         <input type="radio" name="gender" value="남"><span>남자&nbsp;&nbsp;</span>
         <input type="radio" name="gender" value="여"><span>여자</span><br>
         
         <label><b>이메일 주소</b></label>
         <input type=text name=email value="${member.email }" required>
         <span id="email_message"></span><br>
         
         <div class="clearfix">
            <button type=submit class=submitbtn>수정</button>
            <button type=button class=submitbtn
            	onClick="history.back(-1)">취소</button>
         </div>
   	</fieldset>                
   </form>
</div>
</body>
</html>