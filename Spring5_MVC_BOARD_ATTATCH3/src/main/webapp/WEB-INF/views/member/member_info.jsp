<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>


<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../../views/board/header.jsp"></jsp:include>

<div class="container">
<h3>회원정보</h3>
	<table class="table table-striped">
		<!-- <c:set var="m" value="${memberinfo}"/> -->
	
		<tr>
			<td>아이디</td>
			<td>${memberinfo.id}</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>${memberinfo.password}</td>
		</tr>
		<tr>
			<td>이름</td>
			<td>${memberinfo.name}</td>
		</tr>
		<tr>
			<td>나이</td>
			<td>${memberinfo.age}</td>
		</tr>
		<tr>
			<td>성별</td>
			<td>${memberinfo.gender}</td>
		</tr>
		<tr>
			<td>이메일 주소</td>
			<td>${memberinfo.email}</td>
		</tr>
		<tr>
			<td colspan="2"><a href="member_list.net">리스트로 돌아가기</a></td>
		</tr>
	</table>
</div>
</body>
</html>