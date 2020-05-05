$(function(){
	var check = 0;
	
	// ��� ��ư Ŭ���� �� �̺�Ʈ �κ�  
	$('form').submit(function(){
		if ($("#board_subject").val() == '') {
			alert("������ �Է��ϼ���.");
			$('#board_subject').focus();
			return false;
		}
		
		if ($("#board_content").val() == '') {
			alert("������ �Է��ϼ���.");
			$('#board_content').focus();
			return false;
		}
		
		if ($("#board_pass").val() == '') {
			alert("��й�ȣ�� �Է��ϼ���.");
			$('#board_pass').focus();
			return false;
		}
		
		// �ѹ��� �������� ������ $('#filevalue').text()�� ���ϸ��� �����Ѵ�.
		if (check == 0) {
			value = $('#filevalue').text();
			html = "<input type = 'text' value = '" + value + "' name = 'check'>";
			$(this).append(html);
		}
	});
	
	
	show();
	
	function show() {
		// ���� �̸��� �ִ� ��� remove �̹����� ���̰� �ϰ� ���� ���� ������ �ʰ� �Ѵ�.
		if ($('#filevalue').text() == '') {
			$('.remove').css('display', 'none');
		} else {
			$('.remove').css('display', 'inline-block');
		}
	}
	
	$("#upfile").change(function(){
		$("#filevalue").val('');
		console.log($(this).val());
		var inputfile = $(this).val().split('\\');
		$('#filevalue').text(inputfile[inputfile.length-1]);
	});
	
	// remove �̹����� Ŭ���ϸ� ���ϸ��� ''�� �����ϰ� remove �̹����� ������ �ʰ� �Ѵ�.
	$('.remove').click(function(){
		$('#filevalue').text('');
		$(this).css('display', 'none');
	})			
});