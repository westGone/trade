/* Common JS 공통으로 사용되는 함수, 변수 선언 */

// 전역 변수 선언

/***************************유저 기초 정보*******************************/
var userLng = "KR";
var userName = "";
var userAuth = "";

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
	elementObj.userLanguage = userLanguage;
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
	console.log(from, align, icon, type, animIn, animOut, content);
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

