/* Common JS 공통으로 사용되는 함수, 변수 선언 */

// 전역 변수 선언

/***************************유저 기초 정보*******************************/

var userLng 		= "";
var userName 		= "";
var userAuth 		= "";
var gmtTime 		= 9;
var dateFormat 		= "yyyy-MM-dd";

//다국어 처리
if(typeof changeUserLng !== 'undefined'){
	userLng = changeUserLng;
	
	// 다국어 선택 메뉴 변경
	var element = document.getElementsByClassName("selected_language");
	$("#"+element[0].id).removeClass();
	$("#main_"+changeUserLng.toLowerCase()).addClass('selected_language');
}

/***************************공통 함수 선언*******************************/
/**
 * 사용자가 선택한 언어로 Message창을 띄워준다.
 */
function messageLang(msgCode, msgType){
	var params		= new Object();
	params.code		= msgCode;
	params.codeType = 'A';
	
	$.ajax({
		type:'post',
	    url: "/common/getMessageLang.do",
		data: params,
		dataType : "json",
		cache:false,
		async:true,
	    success: function(res) {
			var message = "";
			if(userLng == "KR"){
				message = res.data.CODE_KR;
			}else if(userLng == "EN"){
				message = res.data.CODE_EN;
			}else if(userLng == "CN"){
				message = res.data.CODE_CN;
			}else if(userLng == "JA"){
				message = res.data.CODE_JA;
			}
		
			if(msgType == "Alert"){
				showAlert("top", "center", undefined, "success", undefined, undefined, message);
			}else if(msgType == "Error"){
				showAlert("top", "center", undefined, "danger", undefined, undefined, message);
			}
	    },
	    error:function(e) {
	    	messageLang("A0001", "Error");
        }
	});
}

/**
 * form내의 엘레멘트를 map으로 리턴 (save시 사용)
 * 화면 하나에 여러개의 Form(HTML)이 있을 경우 사용(대상은 하나의 테이블)
 * @param objs([ {screen:'SMS_AA0001_000', form:SMS_AA0001_000_entryForm}, {screen:'SMS_AA0001_100', form:SMS_AA0001_100_entryForm} ])
 * @returns {Object}
 */
function getFormDataToMaps(objs) {
	var elementObj = new Object();

	if(objs != "undefined" && objs != null) {
		for(var i=0; i<objs.length; i++) {
			var screenCode = objs[i].screen;
			var formObj = objs[i].form;

			elementObj = getTabstripDataToMap(screenCode, formObj, elementObj);
		}
	}
	/*
	elementObj.format = dateFormat;
	elementObj.gmtTime = gmtTime;
	elementObj.registerUserId = userId;
	elementObj.updateUserId = userId;
	elementObj.userLng = userLng;
	*/
	return elementObj;
}


/**
 * tabstrip form내의 엘레멘트를 map으로 리턴 (save시 사용)
 * @param screenCode
 * @param formObj
 * @param paramObj
 */
function getTabstripDataToMap(screenCode, formObj, paramObj, onlyScreenCodeYn) {
	var elementObj = null;

	if(paramObj == undefined)	elementObj = new Object();
	else						elementObj = paramObj;

	//해당 스크린코드에 해당하는 엘레멘트만 map에 포함할지 여부
	//html 구조가 form으로 분리하기 어려운 경우 사용하기 위하여 추가
	if(onlyScreenCodeYn == undefined) onlyScreenCodeYn = "N";

	for(var i=0; i<formObj.elements.length; i++) {
		if(onlyScreenCodeYn == "Y" && !startsWith(formObj.elements[i].id, screenCode)) {
			//해당 스크린코드에 해당하는 엘레멘트만 map에 포함해야 하는데 스크린코드 엘레멘트가 아닌 경우 PASS
		} else {
			//대소문자 구분안함
			if(!endsWith(formObj.elements[i].id, "btn") && !endsWith(formObj.elements[i].id, "search")) {
				//대소문자 구분안함
				//날짜인 경우 오라클 기본 포맷인 yyyy-mm-dd 포맷으로 변환 후 map에 담는다.
				if(endsWith(formObj.elements[i].id, "date") && formObj.elements[i].value != "") {
					/*
					var myDate = new Date(formObj.elements[i].value);
					elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = myDate.getFullYear() + "-" + leftPad(myDate.getMonth() + 1 + "", "0", 2) + "-" + leftPad(myDate.getDate() + "", "0", 2);
					*/
					elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = formObj.elements[i].value;
				//radio button check
				} else if(formObj.elements[i].type == "radio") {
					if(formObj.elements[i].checked) {
						elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = formObj.elements[i].value;
					} else {
						if(elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] == null) {
							elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = "";
						}
					}
				// 숫자이면서 값이 공백인 경우 0으로 세팅
				}
				/* 
				 else if($("#"+formObj.elements[i].id).attr("boxType") == "numeric" && formObj.elements[i].value == "") {
					elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = "0";
				//checkbox check
				}
				*/
				 else if(formObj.elements[i].type == "checkbox") {
					if(formObj.elements[i].checked) elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = "Y";
					else elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = "N";
				} else {
					elementObj[formObj.elements[i].id.replace(screenCode+"_", "")] = formObj.elements[i].value;
				}
			}
		}
	}
	return elementObj;
}

/*
 * java.lang.String.endsWith() 구현
 */
function endsWith(str, checker){
    if(str!=null && checker!=null && str.length > checker.length){
        if(str.substr(str.length-checker.length).toUpperCase() == checker.toUpperCase()){
            return true;
        }else{
            return false;
        }
    }else{
        return false;
    }
}
/*
 * Alert 표현
 */
function showAlert(from, align, icon, type, animIn, animOut, content){
    $.growl({
        icon: icon,
        title: '',
        message: content,
        url: ''
    },{
        element: 'body',
        type: type,
        allow_dismiss: true,
        placement: {
            from: from,
            align: align
        },
        offset: {
            x: 30,
            y: 30
        },
        spacing: 10,
        z_index: 999999,
        delay: 2500,
        timer: 1000,
        url_target: '_blank',
        mouse_over: false,
        animate: {
            enter: animIn,
            exit: animOut
        },
        icon_type: 'class',
        template: '<div data-growl="container" class="alert" role="alert">' +
        '<button type="button" class="close" data-growl="dismiss">' +
        '<span aria-hidden="true">&times;</span>' +
        '<span class="sr-only">Close</span>' +
        '</button>' +
        '<span data-growl="icon"></span>' +
        '<span data-growl="title"></span>' +
        '<span data-growl="message"></span>' +
        '<a href="#" data-growl="url"></a>' +
        '</div>'
    });
}

/**
 * 날짜변환
 * @param obj
 * @param returnType(S:String Type, D:Date Type)
 * @returns
 */
function convertDateToString(obj, returnType) {
	// Return Type이 정의되지 않았을 경우 Default String Type이다.
	if(returnType != "D")	returnType = "S";
	
	try {
		if(obj != null && obj != "" && obj.length != 10) {
			var year = 1900 + obj.year;
			var month = leftPad(obj.month+1+"", "0", 2);
			var day = leftPad(obj.date+"", "0", 2);
	
			var tmpDate = new Date(year + "-" + month + "-" + day + "T00:00");
			
			if(returnType === "S")	obj = tmpDate.toString(dateFormat);
			else					obj = tmpDate;
		}
	} catch (e) {
		obj = new Date(obj.time);
	}
	return obj;
}

//엔트리 입력일시/수정일시 표현시 사용
function convertDateToString2(obj) {
	if(obj != null)	{
		obj = new Date(obj.time);
		obj.setHours(obj.getHours() + obj.getTimezoneOffset()/60);
		obj = obj.toString(dateFormat + " HH:mm");
	}
	return obj;
}

//Format에 맞게 변경
function convertDateToString3(dateStr, format) {
	
	var year = "";
	var month = "";
	var day = "";		
	
	if(dateStr != null && dateStr != "") {
		if (dateFormat == "yyyy-MM-dd" || dateFormat == "yyyy/MM/dd") {
			year = dateStr.substring(0,4);
			month = dateStr.substring(5,7);
			day = dateStr.substring(8,10);			
		} else if (dateFormat == "MM-dd-yyyy" || dateFormat == "MM/dd/yyyy") {
			year = dateStr.substring(6,10);
			month = dateStr.substring(0,2);
			day = dateStr.substring(3,5);
		} else if (dateFormat == "dd-MM-yyyy" || dateFormat == "dd/MM/yyyy") {
			year = dateStr.substring(6,10);
			month = dateStr.substring(3,5);
			day = dateStr.substring(0,2);
		}
		dateStr = new Date(year, month - 1, day).toString(format);
	}
	return dateStr;
}

function convertDateToString4(obj, format) {
	if(obj != null)	{
		var year = 1900 + obj.year;
		var month = leftPad(obj.month+1+"", "0", 2);
		var day = leftPad(obj.date+"", "0", 2);
		var hours = leftPad(obj.hours+"", "0", 2);
		var minutes = leftPad(obj.minutes+"", "0", 2);
		
		var tmpDate = new Date(year + "-" + month + "-" + day + "T" + hours + ":" + minutes);
		obj = tmpDate.toString(format);
	}
	return obj;
}

//엔트리 입력일시/수정일시 표현시 사용 && 그리드 일시표현시 사용
function convertDateToString5(obj) {
	var tmpDate = null;

	if(obj != null)	{
		var year = 1900 + obj.year;
		var month = leftPad(obj.month+1+"", "0", 2);
		var day = leftPad(obj.date+"", "0", 2);
		var hours = leftPad(obj.hours+"", "0", 2);
		var minutes = leftPad(obj.minutes+"", "0", 2);

		tmpDate = new Date(year + "-" + month + "-" + day + "T" + hours + ":" + minutes);
		tmpDate = tmpDate.toString(dateFormat + " HH:mm"); 
	}

	return tmpDate;
}

/**
 * 날자 객체를 이용한 문자열 return
 * @param obj
 * @param option
 * @returns {String}
 */
function convertObjToDateString(obj, option) {
	var date     = new Date(obj);
	
	var format   = option != undefined ? option.format != undefined ? option.format : "yyyy-MM-dd"  : "yyyy-MM-dd";
	var retDate  = "";
	
	var ch       = format.replace(/[^\/\-]/gi , "");
	var delim    = (ch != null && ch.length > 0) ? ch.substr(0, 1) : "-";
	var iy       = format.indexOf("y");
	var im       = format.indexOf("M");
	var id       = format.indexOf("d");
	
	if(im < id && id < iy){
		retDate += overNine((date.getMonth() + 1)) + delim + overNine(date.getDate()) + delim + date.getFullYear();
	}else if(id < im && im < iy){
		retDate += overNine(date.getDate()) + delim + overNine((date.getMonth() + 1)) + delim + date.getFullYear();
	}else{
		retDate += date.getFullYear() + delim + overNine((date.getMonth() + 1)) + delim + overNine(date.getDate());
	}
	
	return retDate;
	
	// 10 미만 값 앞에 "0" 붙이기
	function overNine(num){
		return (num > 9 ? "" + num : "0" + num); 
	}
}

function convertDateToStringFormat(obj){
	obj = new Date(obj.time);
	obj.setHours(obj.getHours() + obj.getTimezoneOffset()/60);
	obj = obj.toString(dateFormat + " HH:mm");
		
	var returnStr = new Date(obj);
	
	returnStr = returnStr.getFullYear() +"-" +returnStr.getMonth()+1 +"-" +returnStr.getDate()
				+" "+returnStr.getHours() +":" +returnStr.getMinutes()

    return returnStr;
}

/**
 * 일반 Text Box readonly 처리
 * @param tagName
 * @param flag  (true: readonly)
 */
function inputboxStyleChange(tagName, flag) {
	//콤보 박스 처리
	if($("#"+tagName).attr("boxType") == "dropdownlist") {
		if(flag) {
			$("#"+tagName).data("kendoDropDownList").readonly();
			$("#"+tagName).prev("span.k-state-default").css("background", "#ffffff");
		} else {
			$("#"+tagName).data("kendoDropDownList").enable();
			$("#"+tagName).prev("span.k-state-default").css("background", "#ffffff");
		}
	//컬러 픽커 처리
	} else if($("#"+tagName).attr("boxType") == "colorpicker") {
		if(flag) {
			$("#"+tagName).data("kendoColorPicker").enable(false);
			//$("#"+tagName).prev("span.k-state-default").css("background", "#ffffff");
		} else {
			$("#"+tagName).data("kendoColorPicker").enable(true);
			//$("#"+tagName).prev("span.k-state-default").css("background", enable_color);
		}
	//숫자형 텍스트 박스 처리
	} else if($("#"+tagName).attr("boxType") == "numeric") {
		if(flag) {
			$("#"+tagName).data("kendoNumericTextBox").enable(false);
			$("#"+tagName).prev(".k-input").css("background", "#ffffff");
			$("#"+tagName).parent("span.k-expand-padding").css("background", "#ffffff");
			$("#"+tagName).parent("span.k-expand-padding").css("background", "#ffffff");
		} else {
			$("#"+tagName).data("kendoNumericTextBox").enable();
			$("#"+tagName).prev(".k-input").css("background", "#ffffff");
			$("#"+tagName).parent("span.k-expand-padding").css("background", "#ffffff");
		}
	//Radio 처리
	} else if($("#"+tagName).attr("type") == "radio") {
		if(flag) {
			$("[id=" + tagName + "]:not(:checked)").attr("disabled", true);
		} else {
			$("[id=" + tagName + "]").removeAttr("disabled");
		}
	//Checkbox 처리 (blue)
	} else if($("#"+tagName).attr("type") == "checkbox") {
		$("input[id=" + tagName + "]").attr("disabled",flag);
	// File Upload 박스 처리
	} else if($("#"+tagName).attr("boxType") == "file") {
		// 파일 업로드 대기열 파일 리스트 및 업로드된 파일정보 삭제
		var up = $("#"+tagName).data("kendoUpload");
		up.wrapper.find('.k-file').find(".k-delete").parent().click();

		if(flag) {
			$("#"+tagName).data("kendoUpload").disable();
		} else {
			$("#"+tagName).data("kendoUpload").enable();
		}
	//중복체크버튼 처리
	} else if($("#"+tagName).attr("boxType") == "check") {
		if(flag) {
			$("#"+tagName).kendoButton({
		        imageUrl: "/images/blueopal/btn_check_d.gif"
		    });
			$("#"+tagName).data("kendoButton").enable(false);
		} else {
			$("#"+tagName).kendoButton({
		        imageUrl: "/images/blueopal/btn_check.gif"
		    });
			$("#"+tagName).data("kendoButton").enable(true);
		}
	//버튼 처리
	} else if(endsWith(tagName, "btn") || $("#"+tagName).attr("type") == "button") {
		if(flag) {
			$("#"+tagName).data("kendoButton").enable(false);
		} else {
			$("#"+tagName).data("kendoButton").enable(true);
		}
	//날짜 검색 아이콘 처리
	} else if(endsWith(tagName, "date") || $("#"+tagName).attr("boxType") == "date") {
		if($("#"+tagName).data("kendoDatePicker") != undefined) {
			if(flag) {
				$("#"+tagName).data("kendoDatePicker").enable(false);
				$("#"+tagName).parent().parent("span.k-header").css("background", "#ffffff");
			} else {
				$("#"+tagName).data("kendoDatePicker").enable(true);
			}
		}
	//기타 항목 처리
	} else {
		// Input type이 버튼형이 아닐 경우에만 적용
		if($("#"+tagName).attr("type") != "button") {
			if($("#"+tagName).data("kendoMaskedTextBox") != undefined) {
				if(flag) {
					$("#"+tagName).data("kendoMaskedTextBox").enable(false);
					$("#"+tagName).css("background", "#ffffff");
				} else {
					$("#"+tagName).data("kendoMaskedTextBox").enable();
					$("#"+tagName).css("background", "#ffffff");
				}
			} else {

				if(flag) {
					$("#"+tagName).attr("readonly",true);
					$("#"+tagName).css("background", "#ffffff");

					$("#"+tagName).focus(function(){
						$(this).css("background", "#ffffff");
						$(this).parent("span.k-space-right").css({"background":"#ffffff","border-color":"#e0e0e0"});
					}).blur(function() {
					    $(this).css("background", "#ffffff");
					    $(this).parent("span.k-space-right").css({"background":"#ffffff","border-color":"#e0e0e0"});
					});

					$("#"+tagName).parent("span.k-space-right").css({"background":"#ffffff","border-color":"#e0e0e0"});
					$("#"+tagName).parent("span.k-space-left").css("background", "#ffffff");
				} else {
					$("#"+tagName).removeAttr("readonly");
					$("#"+tagName).css("background", "#ffffff");

					$("#"+tagName).focus(function(){
						$(this).css("background", "#ffffff");
						$(this).parent("span.k-space-right").css({"background": "#ffffff","border-color":"#ffffff"});
					}).blur(function() {
					    $(this).css("background", "#ffffff");
					    $(this).parent("span.k-space-right").css({"background":"#ffffff"});
					});

					$("#"+tagName).parent("span.k-space-right").css("background", "#ffffff");
					$("#"+tagName).parent("span.k-space-left").css("background", "#ffffff");
				}
			}
		}

		if($("#"+tagName).attr("boxType") == "editor") {
			if(flag) {
				var editor = $("#"+tagName).data("kendoEditor");
				$(editor.body).attr("contenteditable", false);
				editor.wrapper.find(".k-editor-toolbar").css("display", "none");
			} else {
				var textarea = $("#"+tagName);
				var wrapper = textarea.closest(".k-editor");
				// get decoded textarea value
				var value = $("<div></div>").html(textarea.val()).text();
				textarea.show().insertBefore(wrapper).val(value);
				wrapper.remove();
				setEditor(tagName, $("#"+tagName).attr("boxValue"));
			}
		}
	}
}

/**
 * 웹페이지 다국어 처리
 * 사용언어가 한국어일 경우에는 Default 값을 보여줌(데이터를 가져오지 않는다.)
 * @param screenCode
 * @param scriptLabel(Script에서 동적으로 HTML 페이지를 생성할 경우 해당 Label의 class명을 넘겨준다.)
 */
function setPageLabels(screenCode, scriptLabel) {
	var labelList = [];
	var labelCount = 0;
	if(userLng == ""){
		userLng = "KR"
	}
	if(scriptLabel == undefined || scriptLabel == null || scriptLabel == "")	scriptLabel = "_label";
	else																		scriptLabel = "_" + scriptLabel;

	$("." + screenCode + scriptLabel).each(function (index, item) {
		if($(item).html() != undefined && $(item).html() != null && $(item).html() != "")
			if(userLng == "KR") {
				if($(item).attr("msg") == undefined || $(item).attr("msg") == null || $(item).attr("msg") == "") {
					labelList[labelCount++] = {id: $(item).html()};
				} else {
					$(item).html($(item).attr("msg"));
				}
			} else {
				labelList[labelCount++] = {id: $(item).html()};
			}
	});

	if(labelList.length > 0) {
		var labelDataSource = loadLanguageLabel(labelList);
		$("." + screenCode + scriptLabel).each(function (index, item) {
			for(var i = 0; i < labelDataSource.length; i++) {
				var data = labelDataSource[i];
				if($(item).html() == data.message_id) {
					$(item).html(data.message_name);
					break;
				}
			}
		});
	}

	$("." + screenCode + scriptLabel).css("display","inline");
};

/**
 * 다국어 Label 가져오기
 * @param labelList
 * @returns {kendo.data.DataSource}
 */
function loadLanguageLabel(labelList) {
	var labelDataSource = null;
	
	$.ajax({
        url: "/common/common/message.do",
		data: { params: JSON.stringify({param: labelList, userLangType: userLng}) },
		dataType : "json",
        type:"post",
        cache: false,
        async: false,
        success: function(res) {
        	labelDataSource = res.data;
        }
    });

	return labelDataSource;
}
