/* 게시판 화면을 Control 하는 JS */

settings_action();

function settings_action(){
	
	$('#coinBoard_boardList').DataTable({
		ajax: {
	        url: '/board/getBoardList.do',
			type: 'POST',
			data: { params : JSON.stringify({boardType : $("#boardType").val()})},
			dataSrc: 'data'
	    },
		columns: [
			{"data": "code"},
			{"data": "code_type"},
			{"data": "code_kr"},
			{"data": "code_kr"},
			{"data": "code_kr"}
		]
	});
	
	//작성 버튼 Click Function
	$('#coinBoard_registBtn').on('click', function(){
		$('#exampleModal').modal('show');
	});
	//저장 버튼 Click Function
	$('#coinBoard_saveBtn').on('click', function(){
		coinBoard_saveConten();
	});
	
	//Modal Close Function
	$('#exampleModal').on('hide.bs.modal', function (e) {
		$("#coinBoard_title").val("");
		$("#coinBoard_content").val("");
	});
	
}

/*
 * 게시글 저장
 */
function coinBoard_saveConten(){
	
	if($("#coinBoard_title").val() == ""){
		//제목을 입력해 주세요.
		messageLang("A0003", "Error");
	}
	
	if($("#coinBoard_content").val() == ""){
		//내용을 입력해 주세요.
		messageLang("A0004", "Error");
	}
	
	var params = new Object();
	params.title 		= $("#coinBoard_title").val();
	params.content 		= $("#coinBoard_content").val();
	params.boardType	= $("#boardType").val();
	params.userId		= "관리자";
	
	$.ajax({
		type:'post',
	    url: "/board/saveContent.do",
		data: params,
		dataType : "json",
		cache:false,
		async:true,
	    success: function(res) {
			messageLang("A0001", "Alert");
			
	    	$("#settings_codeEn").val(res.en.message.result.translatedText);
	    	$("#settings_codeCn").val(res.cn.message.result.translatedText);
	    	$("#settings_codeJa").val(res.ja.message.result.translatedText);
	
	    },
	    error:function(e) {
	    	messageLang("A0000", "Error");
	    }
	});
}

/* 
function codeTransBtn_click(){
	if($("#settings_codeKr").val()!=""){
		var params = new Object();
		params.beforeTranslation =  $("#settings_codeKr").val();
		
		$.ajax({
			type:'post',
		    url: "/trans/translation.do",
			data: params,
			dataType : "json",
			cache:false,
			async:true,
		    success: function(res) {
				messageLang("A0001", "Alert");
				
		    	$("#settings_codeEn").val(res.en.message.result.translatedText);
		    	$("#settings_codeCn").val(res.cn.message.result.translatedText);
		    	$("#settings_codeJa").val(res.ja.message.result.translatedText);

		    },
		    error:function(e) {
		    	messageLang("A0000", "Error");
	        }
		});
	}else{
    	messageLang("A0002", "Error");
	}
}
*/