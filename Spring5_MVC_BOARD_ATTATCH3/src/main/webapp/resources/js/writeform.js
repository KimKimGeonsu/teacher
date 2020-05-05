$(document).ready(function() {
	
	// 등록버튼 클릭할때 이벤트부분
	$("form").submit(function() {

		if ($.trim($("input").eq(1).val()) == "") {
			alert("비밀번호를 입력하세요");
			$("input:eq(1)").focus();
			return false;
		}
		if ($.trim($("input").eq(2).val()) == "") {
			alert("제목을 입력하세요");
			$("input:eq(2)").focus();
			return false;
		}
	
	});

	show();
	function show() {
		// 파일 이름이 있는 경우 remove 이미지를 보이게 하고 없는 경우는 보이지 않게 합니다.
		if ($('#filevalue').text() == '') {
			$(".remove").css("display", "none");
		} else {
			$(".remove").css("display", "inline-block");
		}
	}

	$("#upfile").change(function() {
		$('#filevalue').val('');
		console.log($(this).val());
		var inputfile = $(this).val().split('\\');
		$('#filevalue').text(inputfile[inputfile.length - 1]);
	})

	// remove 이미지를 클릭하면 파일명을 ''로 변경하고 remove 이미지를 보이지 않게 합니다.
	$(".remove").click(function() {
		$("#filevalue").text('');
		$(this).css('display', 'none');
		$("input[name=BOARD_ORIGINAL]").val('');
		$("input[name=BOARD_FILE]").val('');
	})
})