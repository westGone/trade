/* Main화면을 Control 하는 JS - 상단, 좌측바의 기능구현 */

//첫 시작시 Index 화면 출력
$("#mainChangeScreen").load("index");

//Main화면에서 기초 Data Setting
main_action();

function main_action(){
	//Setting 화면 전환
	document.getElementById("mainLeftSettings").onclick = function() {$("#mainChangeScreen").load("settings");};

	//Profile 화면 전환
	document.getElementById("profile").onclick = function() {$("#mainChangeScreen").load("profile");};
	
	//News 화면 전환
	document.getElementById("newsView").onclick = function() {$("#mainChangeScreen").load("news");};
	
	//Chart 화면 전환
	document.getElementById("chartMoris").onclick = function() {$("#mainChangeScreen").load("chart-morris");};
	
	//게시판 화면 전환
	document.getElementById("coinBoardBt").onclick = function() {
		$("#boardType").val("BT");
		$("#mainChangeScreen").load("coinBoard");
	};
	document.getElementById("coinBoardEt").onclick = function() {
		$("#boardType").val("ET");
		$("#mainChangeScreen").load("coinBoard");
	};
	document.getElementById("coinBoardRp").onclick = function() {
		$("#boardType").val("RP");
		$("#mainChangeScreen").load("coinBoard");
	};
	document.getElementById("coinBoardOt").onclick = function() {
		$("#boardType").val("OT");
		$("#mainChangeScreen").load("coinBoard");
	};
	
	document.getElementById("main_krA").onclick = function() {
		main_changeLng("main_kr");
	};
	document.getElementById("main_enA").onclick = function() {
		main_changeLng("main_en");
	};
	document.getElementById("main_cnA").onclick = function() {
		main_changeLng("main_cn");
	};
	document.getElementById("main_jaA").onclick = function() {
		main_changeLng("main_ja");
	};
	setPageLabels("main");
}

// 다국어 처리
function main_changeLng(lngType){
	// 유저 다국어 Key값 변경
	if(lngType == "main_kr"){
		changeUserLng = "KR"
	}else if(lngType == "main_en"){
		changeUserLng = "EN"
	}else if(lngType == "main_cn"){
		changeUserLng = "CN"
	}else if(lngType == "main_ja"){
		changeUserLng = "JA"
	}
	setTimeout(function(){
		$("#main_body").load("main");
	},100);
}