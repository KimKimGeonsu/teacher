<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<jsp:include page="header.jsp" />
<script src="js/writeform.js" charset="UTF-8"></script>
<style>
tr.center-block {
	text-align: center
}

h1 {
	font-size: 1.5rem;
	text-align: center;
	color: #1a92b9
}

.container {
	width: 60%
}

label {
	font-weight: bold
}

#upfile {
	display: none
}

img {
	width: 20px;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<form action="BoardReplyAction.bo" method="post"
			 name="boardform">
			 
			 <!-- 답변을 추가하기 위해서는 답변의 원문글에 대한
			 BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ 정보가 필요합니다. -->
			 <input type="hidden" name="BOARD_RE_REF" value="${boarddata.BOARD_RE_REF }">
			 <input type="hidden" name="BOARD_RE_LEV" value="${boarddata.BOARD_RE_LEV }">
			 <input type="hidden" name="BOARD_RE_SEQ" value="${boarddata.BOARD_RE_SEQ }">

			<h1>MVC 게시판-Reply 페이지</h1>
			<div class="form-group">
				<label for="board_name">글쓴이</label> <input name="BOARD_NAME"
					id="board_name" value="${id }" readOnly type="text" size="10"
					maxlength="30" class="form-control" placeholder="Enter_board_name">

			</div>

			<div class="form-group">
				<label for="board_subject">제목</label> <input name="BOARD_SUBJECT"
					id="board_subject" type="text" size="50" maxlength="100"
					class="form-control" placeholder="Enter_board_subject" value="Re: ${boarddata.BOARD_SUBJECT }" readOnly>

			</div>
			<div class="form-group">
				<label for="board_content">내용</label>
				<textarea name="BOARD_CONTENT" id="board_content" cols="67"
					rows="10" class="form-control"></textarea>


			</div>
			
			<div class="form-group">
			<label for="board_pass">비밀번호</label>
			<input name="BOARD_PASS" id="board_pass" type="password" class="form-control" required="required">
			</div>

			<div class="form-group">
				<button type="submit" class="btn btn-primary">등록</button>
				<button type="reset" class="btn btn-danger">취소</button>
			</div>

		</form>
	</div>



</body>


</html>