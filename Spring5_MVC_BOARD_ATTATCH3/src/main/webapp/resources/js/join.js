$(function(){
	
	var checkid;	
	var checkemail;
	
	// id 유효성 검사
	$('input:eq(0)').on('keyup', function(){
		$('#message').empty();
		
		var pattern = /^\w{5,12}$/; 
		var id = $('input:eq(0)').val();
		if (!pattern.test(id)) {	
			$('#message').html('형식에 맞게 입력해주세요. 5~12자 내로 입력하세요.')
						 .css('color', 'red');
			checkid = false;
			return;
		}
		
		$.ajax({
			url : 'idcheck.net',	
			data : {"id" : id},	
			success : function (result) {
				if (result == -1) {	
					$('#message').css('color', 'green').html("사용가능한 아이디 입니다.");
					checkid = true;
				} else {
					$('#message').css('color', 'blue').html("이미 존재하는 아이디 입니다.");
					checkid = false;
				}
			} 
		}); 
	});	
	
	$("input:eq(6)").on('keyup', function(){
		$("#email_message").empty();

		var pattern = /\w+@\w+[.]\w{3}/;
		var email = $('input:eq(6)').val();
		console.log(email);
		console.log(!pattern.test(email));
		if (!pattern.test(email)) {
			$("#email_message").css('color', 'red')
							   .html("이메일 형식에 맞게 입력해주세요.");
			checkemail = false;
		} else {
			$("#email_message").css('color', 'green')
			   				   .html("올바른 형식입니다.");
			checkemail = true;
		} // if
	});	// keyup
	
	
	$('form').submit(function(){
		if (!checkid) {
			alert("아이디를 다시 입력해주세요.");
			$('input:eq(0)').val('').focus();
			$('#message').text('');
			return false;
		}
		
		if (!$.isNumeric($("input[name='age']").val())) {
			alert("나이는 숫자만 입력하세요.");
			$("input[name='age']").val('').focus();
			return false;
		}
		
		if (!checkemail) {
			alert("이메일을 다시 입력해주세요.");
			$("input[name='email']").val('').focus();
			return false;
		}
	});
});	