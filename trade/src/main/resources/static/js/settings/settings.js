/* Settings 화면을 Control 하는 JS */

settings_action();

function settings_action(){
	$('#settings_codeListGrid').DataTable( {
	    ajax: {
	        url: '/settings/getCodeDefineList.do',
			type: 'POST',
			dataSrc: 'data'
	    },
		columns: [
			{"data": "code"},
			{"data": "code_type"},
			{"data": "code_kr"}
		]
	});
}

/* Code 저장 Function */ 
function codeTransSaveBtn_click(){
	if($("#settings_code").val()!=""){
		var params = getFormDataToMaps([ {screen:'settings', form:settings_entryForm}]);
		
		$.ajax({
			type:'post',
		    url: "/common/saveCodeDefine.do",
			data: params,
			dataType : "json",
			cache:false,
			async:true,
		    success: function(res) {
				messageLang("A0001", "Alert");
		    },
		    error:function(e) {
		    	messageLang("A0000", "Error");
	        }
		});
	}else{
    	messageLang("A0002", "Error");
	}
}

/* Code 저장 Function */ 
function ttestBtn_click(){
	var params = getFormDataToMaps([ {screen:'settings', form:settings_entryForm}]);
		
	$.ajax({
		type:'post',
	    url: "/news/crawlingNews.do",
		data: params,
		dataType : "json",
		cache:false,
		async:true,
	    success: function(res) {
			messageLang("A0001", "Alert");
	    },
	    error:function(e) {
	    	messageLang("A0000", "Error");
        }
	});
}
/* Code 번역 Function */
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


/* DMI Function */ 
function dmiBtn_click(){
	var params = getFormDataToMaps([ {screen:'settings', form:settings_entryForm}]);
		
	$.ajax({
		type:'post',
	    url: "/autoTrade/autoTrade.do",
		data: params,
		dataType : "json",
		cache:false,
		async:true,
	    success: function(res) {
			messageLang("A0001", "Alert");
	    },
	    error:function(e) {
	    	messageLang("A0000", "Error");
        }
	});
}