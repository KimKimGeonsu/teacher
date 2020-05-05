<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>


<title>Insert title here</title>
<jsp:include page="../../views/board/header.jsp" />
<style>
.center-block {
	display: flex;
	justify-content: center;
}
</style>
<script>
	$(function() {
		$("tr > td:nth-child(3) > a").click(function(event) {
			var answer = confirm("정말 삭제?");
			if (!answer)
				event.preventDefault();

		});

	});
</script>
</head>
<body>


	<div class="container">
		<form action="member_list.net">
			<div class="input-group">
				<select id="viewcount" name="search_field">
					<option value="0" selected>아이디</option>
					<option value="1">이름</option>
					<option value="2">나이</option>
					<option value="3">성별</option>

				</select> <input name="search_word" type="text" class="form-control"
					placeholder="Search" value="${search_word }">
				<button class="btn btn-danger" type="submit">검색</button>

			</div>
		</form>

		<table class="table table-striped">

			<tr>
				<th colspan="3">MVC 멤버 - list</th>

			</tr>

			<tr>
				<th width="20%"><div>아이디</div></th>
				<th width="20%"><div>이름</div></th>
				<th width="20%"><div>삭제</div></th>

			</tr>

			<c:forEach var="m" items="${totallist}">
				<tr>
					<td><a href="member_info.net?id=${m.id }">${m.id }</a></td>
					<td>${m.name }</td>
					<td><a href="member_delete.net?id=${m.id }">삭제</a></td>
				</tr>
				<!-- dd -->

			</c:forEach>


		</table>

		<div class="center-block">
			<div class="row">
				<div class="col">
					<ul class="pagination">
						<c:if test="${page <= 1}">
							<li class="page-item"><a class="page-link" href="#">이전&nbsp;</a>
							</li>
						</c:if>
						<c:if test="${page > 1 }">
						<li class="page-item">
						<a href="member_list.net?page=${page-1}&search_field=${search_field}&search_word=${search_word}"
							class="page-link">이전</a>&nbsp;
						</li>
					</c:if>

							<c:forEach var="a" begin="${startpage}" end="${endpage}">
						<c:if test="${a == page }">
							<li class="page-item">
							<a class="page-link current" href="#">${a}</a>
							</li>
						</c:if>
						<c:if test="${a != page }">
							<li class="page-item">
								<a href="member_list.net?page=${a}&search_field=${search_field}&search_word=${search_word}"
									class="page-link">${a}</a>
							</li>
						</c:if>
					</c:forEach>

						<c:if test="${page >= maxpage }">
							<li class="page-item"><a class="page-link" href="#">&nbsp;다음</a>
							</li>
						</c:if>
						<c:if test="${page < maxpage }">
						
							<li class="page-item"><a
								href="member_list.net?page=${page+1}&search_field=${search_field}&search_word=${search_word}"
								class="page-link">&nbsp;다음</a></li>
						</c:if>
					</ul>


				</div>
			</div>
		</div>


		<br>

	</div>
</body>
<script>
	$(function() {
		var selectedValue = "${search_field}";
		if (selectedValue != '-1')
			$("#viewcount").val(selectedValue);

		$('button').click(function() {

			if ($("input").val() == '') {
				alert("검색어를 입력하세요.");
				return false;
			}

		});

		$("#viewcount").change(function() {
			selectedValue = $(this).val();
			$("input").val('');
			if (selectedValue == '3') {
				$("input").attr("placeholder", "여 또는 남을 입력하세요.");
			}
		});
	})
</script>
</html>