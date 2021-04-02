/* 뉴스 화면을 Control 하는 JS */

news_action();

function news_action(){
	
	var dataTable = $('#news_boardList').DataTable({
		ajax: {
	        url: '/news/getNewsList.do',
			type: 'POST',
			data: { params : JSON.stringify({newsDate : dateFormat})},
			dataSrc: 'data'
	    },
		columns: [
			{"data": "rnum"},
			{"data": "news_title"},
			{"data": "news_url",
				"render": function(data, type, row){
					if(type=='display'){
						data = '<a href="'+ data + '"target ="_blank">' + data + '</a>';
					}
				return data;}},
			{"data": "news_date"}
				//render: function(data) {
                   //return convertDateToStringFormat(data);}}
		]
	});
	
	//Modal Close Function
	$('#newsModal').on('hide.bs.modal', function (e) {
		$("#news_title").val("");
		$("#news_content").val("");
	});
	
	//DataTable 더블클릭 Function
	$('#news_boardList tbody').on('dblclick', 'tr', function () {
		
		var data 			= dataTable.row( this ).data();
		var params 			= new Object();
		params.seqNo		= data.seq;
		
		$.ajax({
			type:'post',
		    url: "/news/getNewsDetail.do",
			data: params,
			dataType : "json",
			cache:false,
			async:true,
		    success: function(res) {
				inputboxStyleChange("news_title"	, true);
				inputboxStyleChange("news_content"	, true);
				$("#news_title").val(res.data.NEWS_TITLE);
				$("#news_content").val(res.data.NEWS_CONTENT);
				$('#newsModal').modal('show');
		    },
		    error:function(e) {
		    	messageLang("A0000", "Error");
		    }
		});
		
	});
}
