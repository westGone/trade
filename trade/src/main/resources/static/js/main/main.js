/* Main화면을 Control 하는 JS - 상단, 좌측바의 기능구현 */

//첫 시작시 Index 화면 출력
$("#mainChangeScreen").load("index");

//Main화면에서 기초 Data Setting
main_action();

function main_action(){
	document.getElementById("mainLeftSettings").onclick = function() {
		$("#mainChangeScreen").load("settings");
	};
}