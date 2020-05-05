<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<jsp:include page="header.jsp" />
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
   src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<style>
#myModal {
   display: none;
}

#count {
   position: relative;
   top: -10px;
   left: -10px;
   background: orange;
   color: white;
   border-radius: 30%
}
</style>
</head>
<body class="container">
   <input type="hidden" id="loginid" value="${id }" name="loginid">
   <table class="table table-striped ">
      <tr>
         <td colspan="2">MVC 게시판 - view 페이지</td>
      </tr>

      <tr>
         <td>글쓴이</td>
         <td>${boarddata.BOARD_NAME}</td>
      </tr>

      <tr>
         <td>제목</td>
         <td>${boarddata.BOARD_SUBJECT}</td>
      </tr>

      <tr>
         <td>내용</td>
         <td><textarea class="form-control" rows="5" readOnly
               style="width: 102%">${boarddata.BOARD_CONTENT}</textarea></td>
      </tr>


      <c:if test="${!empty boarddata.BOARD_FILE}">
         <tr>
            <td>첨부파일</td>
            <td><img src="resources/image/down.png" width="10px"> <a
               href="BoardFileDown.bo?filename=${boarddata.BOARD_FILE}&original=${boarddata.BOARD_ORIGINAL}">
                  ${boarddata.BOARD_ORIGINAL }</a></td>
         </tr>
      </c:if>

      <tr>
         <td colspan="2" class="center">
            <!--<a href="BoardReplyView.bo?num=${boarddata.BOARD_NUM }"> -->
            <button class="btn btn-primary start">댓글</button> <span id="count">${count }</span>
            <!--</a>  --> <c:if
               test="${boarddata.BOARD_NAME == id || id == 'admin'}">
               <a href="BoardModifyView.bo?num=${boarddata.BOARD_NUM }">
                  <button class="btn btn-secondary">수정</button>
               </a>

               <a href="#">
                  <button class="btn btn-success" data-toggle="modal"
                     data-target="#myModal">삭제</button>
               </a>
            </c:if> <a href="BoardList.bo">

               <button class="btn btn-dange">목록</button>
         </a>
         <a href = "BoardReplyView.bo?num=${boarddata.BOARD_NUM}">
                     <button type = "button" class = "btn btn-success">답변</button>
                  </a>
      </tr>


   </table>


   <div class="modal" id="myModal">
      <div class="modal-dialog">
         <div class="modal-content">


            <!-- Modal body -->
            <div class="modal-body">
               <form name="deleteForm" action="BoardDeleteAction.bo" method="post">
                  <input type="hidden" name="num" value="${param.num}"
                     id="BOARD_RE_REF"> <input type="hidden" name="BOARD_FILE"
                     value="${boarddata.BOARD_FILE }">
                  <div class="form-group">
                     <label for="pwd">비밀번호</label> <input type="password"
                        class="form-control" placeholder="Enter password"
                        name="BOARD_PASS" id="board_pass">
                  </div>
                  <button type="submit" class="btn btn-primary">Submit</button>
                  <button type="button" class="btn btn-danger">Close</button>
               </form>
            </div>
         </div>
      </div>
   </div>

   <div id="comment">
      <button class="btn btn-info float-left">총 50자 까지 가능합니다.</button>
      <button id="write" class="btn btn-info float-right">등록</button>
      <textarea rows=3 class="form-control" id="content" maxlength="50"></textarea>
      <table class="table table_striped">
         <thead>
            <tr>
               <td>아이디</td>
               <td>내용</td>
               <td>날짜</td>
            </tr>
         </thead>
         <tbody>

         </tbody>
      </table>
      <div id="message"></div>
   </div>
</body>
<script src="<%=request.getContextPath()%>/resources/js/view3.js"></script>

</html>