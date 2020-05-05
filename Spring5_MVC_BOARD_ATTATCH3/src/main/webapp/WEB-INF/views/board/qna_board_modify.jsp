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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<jsp:include page="header.jsp" />
<!-- <script src="resources/js/writeform.js" charset="UTF-8"></script> -->
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
		<form action="BoardModifyAction.bo" method="post" name="modifyform" enctype="multipart/form-data">
			<input type="hidden" name="BOARD_NUM" value="${boarddata.BOARD_NUM }">
			<input type="hidden" name="BOARD_ORIGINAL" value="${boarddata.BOARD_ORIGINAL }">
			<input type="hidden" name="BOARD_FILE" value="${boarddata.BOARD_FILE}">
			<input type="hidden" name="before_file" value="${boarddata.BOARD_FILE}">
			<h1>MVC 게시판-수정</h1>
			<div class="form-group">
				<label for="board_name">글쓴이</label> <input name="BOARD_NAME"
					id="board_name" value="${boarddata.BOARD_NAME }" readOnly
					type="text" size="10" maxlength="30" class="form-control"
					placeholder="Enter_board_name">
			</div>

			<div class="form-group">
				<label for="board_subject">제목</label> <input name="BOARD_SUBJECT"
					id="board_subject" type="text" size="50" maxlength="100"
					class="form-control" placeholder="Enter_board_subject"
					value="${boarddata.BOARD_SUBJECT }">

			</div>
			<div class="form-group">
				<label for="board_content">내용</label>
				<textarea name="BOARD_CONTENT" id="board_content" cols="67"
					rows="10" class="form-control">${boarddata.BOARD_CONTENT }</textarea>


			</div>

			<!-- 파일이 첨부되어 있으면 -->
			<c:if test="${boarddata.BOARD_RE_LEV == 0}">
				<div class="form-group read">
					<label for="board_file">파일 첨부</label>
					<label for="upfile">
					<img src="resources/image/attach.png" alt="파일첨부">
					</label>
					<input type="file" id="upfile" name="uploadfile">
					<span id="filevalue">${boarddata.BOARD_ORIGINAL }</span>
					<img src="resources/image/remove.png" alt="파일삭제" width="10px" class="remove">
					</div>
		
			</c:if>

			<div class="form-group">
				<label for="board_pass">비밀번호</label> <input name="BOARD_PASS"
					id="board_pass" type="password" maxlength="30" class="form-control"
					placeholder="Enter board_pass" required="required">
			</div>



			<div class="form-group">
				<button type="submit" class="btn btn-primary">수정</button>
				<button type="reset" class="btn btn-danger" onClick="histoty.go(-1)">취소</button>
			</div>


		</form>
	</div>



</body>
<script>
$(document).ready(function() {
	   
	   var check = 0;
	   
	   $(document).submit(function() {
	      if ($.trim($("textarea").val()) == "") {
	         alert("내용을 입력하세요");
	         $("textarea").focus();
	         return false;
	      }
	      
	      if(check == 0){
			   value = $("#filevalue").text();
			   html = "<input type='text' value='"
			   +value + "'name='check'";
			   $(this).append(html);
		   }
	   }); //submit end
	   
	  

	   show();
	   function show() {
	      //파일 이름이 있는 경우 remove 이미지를 보이게 하고 없는 경우는 보이지 않게
	      if ($('#filevalue').text() == ''){
	         $(".remove").css('display', 'none');
	      } else {
	         $(".remove").css('display', 'inline-block');
	      }
	   }
	   
	   
	   $("#upfile").change(function() {
	      check++;
	      var inputfile = $(this).val().split('\\');
	      $('#filevalue').text(inputfile[inputfile.length - 1]);
	      show();
	   });
	   
	   //remove이미지 클릭하면 파일명을 ''로 변경하고 remove이미지 보이지 않게 함
	   $(".remove").click(function() {
	      $('#filevalue').text('');
	      $(this).css('display', 'none');
	   })

	}); //ready end
</script>

</html>