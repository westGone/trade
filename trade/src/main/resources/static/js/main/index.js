/* Index JS */

//Index화면에서 기초 Data Setting
index_action();

function index_action(){
	// 게시판 메뉴 선택 시
	document.getElementById("index_btcTab").onclick = function() {
		index_getBoardList("BT");
	};
	document.getElementById("index_ethTab").onclick = function() {
		index_getBoardList("ET");
	};
	document.getElementById("index_rxpTab").onclick = function() {
		index_getBoardList("RP");
	};
	document.getElementById("index_othTab").onclick = function() {
		index_getBoardList("OT");
	};

	// 현재 가격 선택 시
	document.getElementById("index_btcDiv").onclick = function() {
		createTradingView("BYBIT:BTCUSD");
		index_getCoinPrice();
	};
	document.getElementById("index_ethDiv").onclick = function() {
		createTradingView("BYBIT:ETHUSD");
		index_getCoinPrice();
	};
	document.getElementById("index_eosDiv").onclick = function() {
		createTradingView("BYBIT:EOSUSD");
		index_getCoinPrice();
	};
	document.getElementById("index_xrpDiv").onclick = function() {
		createTradingView("BYBIT:XRPUSD");
		index_getCoinPrice();
	};
	
	index_getNewsList();
	index_getBoardList("BT");
	index_getCoinPrice();
}

// Get Board List Data
function index_getBoardList(boardType){
	
	const table = document.getElementById('index_boardTable');
	$("#index_boardTableBody").empty();
	var params = new Object();
	params.boardType =  boardType;
	
	$.ajax({
		type:'post',
	    url: "/index/getBoardList.do",
		data: params,
		dataType : "json",
		cache:false,
		async:true,
	    success: function(res) {
			for(var i=0; i<res.data.length; i++){
				// 새 행(Row) 추가
				const newRow = table.insertRow();
				  
				// 새 행(Row)에 Cell 추가
				const newCell1 = newRow.insertCell(0);
				const newCell2 = newRow.insertCell(1);
				const newCell3 = newRow.insertCell(2);
				
				newCell1.innerText = res.data[i].title;
				newCell2.innerText = res.data[i].register_user_id;
				newCell3.innerText = res.data[i].board_date;
			}
	    },
	    error:function(e) {
	    	messageLang("A0000", "Error");
        }
	});
}

// Get News List Data
function index_getNewsList(){
	var params = getFormDataToMaps([{screen:'index', form:index_entryForm}]);
		
	$.ajax({
		type:'post',
	    url: "/index/getNewsList.do",
		data: params,
		dataType : "json",
		cache:false,
		async:true,
	    success: function(res) {
			const table = document.getElementById('index_newsTable');
			for(var i=0; i<res.data.length; i++){
				// 새 행(Row) 추가
				const newRow = table.insertRow();
				  
				// 새 행(Row)에 Cell 추가
				const newCell1 = newRow.insertCell(0);
				const newCell2 = newRow.insertCell(1);
				
				newCell1.innerText = res.data[i].news_title;
				newCell2.innerText = res.data[i].news_date;
			}
	    },
	    error:function(e) {
	    	messageLang("A0000", "Error");
        }
	});
}

// Get Current Coin Price
function index_getCoinPrice(){
	var params = getFormDataToMaps([{screen:'index', form:index_entryForm}]);
	
	$.ajax({
		type:'post',
	    url: "/autoTrade/getPriceData.do",
		data: params,
		dataType : "json",
		cache:false,
		async:true,
	    success: function(res) {
			if(res != null){
				var btcTag = "";
				var ethTag = "";
				var eosTag = "";
				var xrpTag = "";
				if(res.btcPercent > 0){
					btcTag = "<i class='fa fa-arrow-up m-r-15 text-c-green'></i>"+res.btcPrice
				}else{
					btcTag = "<i class='fa fa-arrow-down m-r-15 text-c-red'></i>"+res.btcPrice
				}
				if(res.ethPercent > 0){
					ethTag = "<i class='fa fa-arrow-up m-r-15 text-c-green'></i>"+res.ethPrice
				}else{
					ethTag = "<i class='fa fa-arrow-down m-r-15 text-c-red'></i>"+res.ethPrice
				}
				if(res.eosPercent > 0){
					eosTag = "<i class='fa fa-arrow-up m-r-15 text-c-green'></i>"+res.eosPrice
				}else{
					eosTag = "<i class='fa fa-arrow-down m-r-15 text-c-red'></i>"+res.eosPrice
				}
				if(res.xrpPercent > 0){
					xrpTag = "<i class='fa fa-arrow-up m-r-15 text-c-green'></i>"+res.xrpPriec
				}else{
					xrpTag = "<i class='fa fa-arrow-down m-r-15 text-c-red'></i>"+res.xrpPriec
				}
				
				$("#index_btcPrice").html(btcTag);
				$("#index_ethPrice").html(ethTag);
				$("#index_eosPrice").html(eosTag);
				$("#index_xrpPrice").html(xrpTag);
				
				$("#index_btcPer").text(res.btcPercent+"% From Last 24 Hours")
				$("#index_ethPer").text(res.ethPercent+"% From Last 24 Hours")
				$("#index_eosPer").text(res.eosPercent+"% From Last 24 Hours")
				$("#index_xrpPer").text(res.xrpPercent+"% From Last 24 Hours")
			}
	    },
	    error:function(e) {
	    	messageLang("A0000", "Error");
        }
	});
}

function createTradingView(symbol){
	new TradingView.widget(
		{
		"width" : 1590,
		"height" : 800,
		"symbol": symbol,
		"interval": "5",
		"timezone": "Asia/Seoul",
		"theme": "light",
		"style": "1",
	  		"locale": "kr",
		  	"toolbar_bg": "#f1f3f6",
		  	"enable_publishing": false,
		"hide_side_toolbar": false,
		"allow_symbol_change": true,
		"details": true,
		"studies": [
		"DM@tv-basicstudies",
		"PSAR@tv-basicstudies"
		],
		"container_id": "tradingview_1174c"
		}
	);
}