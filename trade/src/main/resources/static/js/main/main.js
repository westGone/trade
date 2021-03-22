/* Main화면을 Control 하는 JS - 상단, 좌측바의 기능구현 */

//첫 시작시 Index 화면 출력
$("#mainChangeScreen").load("index");

//Main화면에서 기초 Data Setting
main_action();

function main_action(){
	//Setting 화면 전환
	document.getElementById("mainLeftSettings").onclick = function() {$("#mainChangeScreen").load("settings");};
	
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
}