	function go(page) {
		var limit = $('#viewcount').val();

		var data = 'limit=' + limit + "&state=ajax&page=" + page; 
		ajax(data);
	}
	
	function setPaging(href, digit) {
		output += '<li class = page-item>';
		gray = '';
		if (href == '') {
			gray = "gray";
		}
		anchor = "<a class = 'page-link " + gray + "'" + href + ">" + digit + "</a></li>";
		output += anchor;
	}
	
	function ajax(data) {
		console.log(data);
		output = "";
		$.ajax({
			type : 'POST',
			data : data,
			url : "BoardListAjax.bo",
			dataType : 'json',
			cache : false,
			success : function(data){
				console.log(data);
				$('#viewcount').val(data.limit);
				$('table').find("font")
						  .text("글 개수 : " + data.listcount);
				
				if (data.listcount > 0) {	// 존재하는 글의 개수가 1개 이상인 경우 
					$("tbody").remove();
					var num = data.listcount - (data.page-1) * data.limit;
					console.log(num);
					output = '<tbody>';
					$(data.boardlist).each(function(index, item) {
				
					
						output += '<tr><td>' + (num--) + '</td>';
						blank_count = item.board_RE_LEV * 2 + 1;
						blank = "&nbsp;";
						for (var a = 0; a < blank_count; a++) {
							blank += '&nbsp;&nbsp;';
						}
						img = "";
						// 답글일 경우 
						if (item.board_RE_LEV > 0) {
							img = "<img src = 'image/AnswerLine.gif'>";
						}
						
						var d = new Date((item.board_DATE));
						
						output += '<td><div>' + blank + img;
						output += '<a href = "./BoardDetailAction.bo?num=' + item.board_NUM + '&page=' + data.page + '">'; 
						output += item.board_SUBJECT + '</a></div></td>';
						output += '<td><div>' + item.board_NAME + '</div></td>';
						output += '<td><div>' + d.getFullYear()+"-"+(d.getMonth() + 1)+"-"+d.getDate() + '</div></td>';
						output += '<td><div>' + item.board_READCOUNT + '</div></td></tr>';
					})
					output += "</tbody>";
					$('table').append(output);	
					$(".pagination").empty();	// 페이징 처리 
					output = "";
					
					digit = '«&nbsp;';
					href = "";
					if (data.page > 1) {
						href = 'href=javascript:go(' + (data.page - 1) + ')';
					}
					setPaging(href, digit);
					
					for (var i = data.startpage; i <= data.endpage; i++) {
						digit = i;
						href = "";
						if (i != data.page) {
							href = 'href=javascript:go(' + i + ')';
						}
						setPaging(href, digit);
					}
					
					digit = '»&nbsp;';
					href = "";
					if (data.page < data.maxpage) {
						href = 'href=javascript:go(' + (data.page + 1) + ')';
					}
					
					setPaging(href, digit);
					
					$('.pagination').append(output);
				} // if (data.listcount > 0) end
				else {
					$(".container table").remove();
					$(".center-block").remove();
					$(".container").append("<font size = 5> 글이 존재하지 않습니다. </font>");
				}
			}, // success end
			error : function(){
				console.log('에러');
			}
		});	// ajax end
	}	// function ajax end

$(function() {
	$('#viewcount').change(function(){
		go(1);	// ������ �������� 1�������� �����Ѵ�.
	}); // change end
	
	$('button').click(function(){
		location.href = "BoardWrite.bo";
	});
})