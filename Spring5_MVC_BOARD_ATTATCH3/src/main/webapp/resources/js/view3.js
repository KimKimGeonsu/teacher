$(function(){
	$('#comment table').hide(); //1
	
	function getList(){
		$.ajax({
			type : "post",
			url : "CommentList.bo",
			data : {"BOARD_RE_REF" : $("#BOARD_RE_REF").val()},
			dataType : "json",
			success : function(rdata){
				if(rdata.length > 0){
					$("#comment table").show(); //문서가 로딩될 때 hide()했던 
					$("#comment thead").show(); //글이 있을 때 hide() 부분을
					$("#comment tbody").empty();
					$("#message").text('');
					output = '';
					$(rdata).each(function(){
						img = '';
						//seesion의 id와 Json에 담겨있는 id가 같으면
						if($("#loginid").val() == this.id){
							img = "<img src='resources/image/pencil2.png' width='15px' " +
									"class='update'>"
								+"<img src='resources/image/remove.png' width='15px' " +
									"class='remove'>"
								+"<input type='hidden' value='" + this.num +"'>";
						}
						output += "<tr><td>" + this.id + "</td>";
						output += "<td>" + this.content + "</td>";
						output += "<td>" + this.reg_date + img + "</td></tr>";
					});
					$("#comment tbody").append(output); //tbody에 output append해줌
				}else{
					$("#message").text("등록된 댓글이 없습니다.");
					$("#comment thead").hide();//2
				}
				$("#count").text(rdata.length);
			}
		})//ajax
	}//getList()
	
	$('.start').click(function(){
		getList();
	})//.start click
	
	//글자수 50개 제한하는 이벤트
	$('#content').on('input',function(){
		length = $(this).val().length;
		if(length > 50){
			length = 50;
			content = content.substring(0,length);
		}
		$('.float-left').text(length + "/50");
	})//content on input
	
	$('#write').on('click',function(){
		buttonText = $("#write").text(); //버튼의 라벨로 add할지 update할지
		content = $("#content").val();
		$(".float-left").text('총 50자까지 가능합니다.');
		if(buttonText == "등록"){ //댓글을 추가하는 경우, buttonText가 "등록"일 경우
			url = "CommentAdd.bo";
		
			data = {"content" : content,
					"id" : $("#loginid").val(),
					"board_re_ref" : $("#BOARD_RE_REF").val()};
		}else { //댓글을 수정하는 경우
			url = "CommentUpdate.bo";
			data = {"num" : num, "content" : content};
			$("#write").text("등록"); // 버튼글자 등록으로 변경
		}
		$.ajax({
			type : "post",
			url : url,
			data : data,
			success : function(result){
				$("#content").val('');
				if(result == 1){
					getList();
				}
			}
		})//ajax
	})//#write click event
	
	//수정누르면 그 에 맞는 num을 보낼꺼임
	//#comment의 '.update' click이벤트
	$("#comment").on('click', '.update', function(){
		before = $(this).parent().prev().text(); //선택한 내용을 가져옵니다.
		//#content에 포커스주면서 value값은 before로 변경
		//this = .update
		$("#content").focus().val(before); //textarea에 수정전 내용을 보여줍니다.
		num = $(this).next().next().val(); //수정할 댓글번호를 저장합니다.
		//num = this기준 다음다음의 값을 가져옴
		$("#write").text("수정완료"); //등록버튼의 라벨을 '수정완료'로 변경합니다.
		$(this).parent().parent().css('background','lightgray');
	})//#comment .update click
	
	//remove.png를 클릭하는 경우
	$("#comment").on('click', '.remove', function(){
		var num = $(this).next().val(); //댓글번호
		//num = this의 다음 value값 = this의 다음값은 댓글의 번호가 hidden으로 들어가있음
		
		$.ajax({
			type : "post",
			url : "CommentDelete.bo",
			data : {"num" : num },
			success : function(result){
				if(result == 1){
					getList();
				}
			}
		})
	})//#comment .remove click
})
