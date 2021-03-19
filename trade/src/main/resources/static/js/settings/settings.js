/* Settings 화면을 Control 하는 JS */


function CC0001_000_nameDefaultOnClick(){
	//메시지명(KOR)의 값이 없을때만 돌게하여 중복 검색을 막는다.
	if($("#SMS_CC0001_000_nameDefault").val()!=""){
		
		var params = new Object();
		params.beforeTranslation =  $("#SMS_CC0001_000_nameDefault").val();
		
		$.ajax({
			type:'post',
		    url: "/common/translation.do",
		    data: params,
		    success: function(res) {
		    	$("#SMS_CC0001_000_nameKor").val($("#SMS_CC0001_000_nameDefault").val());
		    	$("#SMS_CC0001_000_nameEng").val(res.en.message.result.translatedText);
		    	$("#SMS_CC0001_000_nameChi").val(res.cn.message.result.translatedText);
		    	$("#SMS_CC0001_000_nameJpn").val(res.ja.message.result.translatedText);
		    	$("#SMS_CC0001_000_nameSpa").val(res.es.message.result.translatedText);
		    	$("#SMS_CC0001_000_nameVie").val(res.vi.message.result.translatedText);
		    },
		    error:function(e) {
		    	loading(0);
		    	messageLang([{id:'A00001'}], "Error");
	        }
		});
	}else{
    	loading(0);
    	messageLang([{id:'A01210'}], "Error");
	}
}

function booking_000_checkMbl(mblNo){
	if(mblNo == ""){
		messageLang([{id:'A01238'}], "Error");
		return false;
	}
	var params 		= new Object();
	var returnValue = false;
	//기본 정보 Setting
	params.srchEntCode 	= userEntCode;
	params.srchMblNo	= mblNo;

	$.ajax({
		type: "post",
		url:"/g-one/fms/BookingOperation/checkMblNo.do",
		data: params,
		dataType : "json",
		cache:false,
		async:false,
		success:function(res) {
			if(res.data[0].COUNT > 0){
				messageLang([{id:'A01239'}], "Error");
				returnValue = false;
			}else{
				returnValue = true;
			}
		},
		error:function(e) {
			messageLang([{id:'A00001'}], "Error");
		}
	});
	return returnValue;
}
