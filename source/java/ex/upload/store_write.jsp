<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/views/common/include/head.jsp" %>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	<c:if test="${storeVO.idx != 0}">
	<c:forEach var="storeStyleVO" items="${storeStyleVOList}" varStatus="status">
	$("#styleCode${storeStyleVO.styleCode}").attr("checked", true);
	</c:forEach>
	</c:if>
	
	// STYLE 선택시
	$("input[name='styleCodes']").click(function(){
		if($("input[name='styleCodes']:checked").size() > 2){
			alert("스타일은 2개까지만 선택 가능 합니다.");
			$(this).attr("checked",false);
		}
	});
	
	// 로딩페이지
	$(document).ajaxStart(function(){
		$(".alphaLayer").show();
	}).ajaxStop(function(){
		$(".alphaLayer").hide();
	});
	
	//아이템 추가
	$(".itemAdd").click(function () {
	    var childArrayCnt = Number($("#itemDiv")[0].childElementCount);

	    if($($("#itemDiv").children()[0]).css('display') == 'none'){
	    	// 초기화
		    $("input[name='itemVOList[0].idx']").attr('value','0');
		    $("input[name='itemVOList[0].isChange']").attr('value',"N");
		    $("input[name='itemVOList[0].itemName']").attr('value','');
		    $("input[name='itemVOList[0].itemNameEng']").attr('value','');
		    $("input[name='itemVOList[0].price']").attr('value','0');
		    $("input[name='itemVOList[0].content']").attr('value','');
		    $("input[name='itemVOList[0].contentEng']").attr('value','');
		    $("input[name='itemVOList[0].repTag']").attr('value','');
		    $("input[name='itemVOList[0].hashTagCodes']").attr('value','');
		    $("input[name='itemVOList[0].imgFile']").attr('value','');
		    $("div[name='itemVOList[0].itemImgDiv']").html('');
		    
	    	$($("#itemDiv").children()[0]).css('display', 'block');
	    	$("#itemTitle").css('display', 'block');
	    	
	    } else {
		    $add_html = $('#itemDiv table:last').clone(true);
		    $('#itemDiv').append($add_html);
		    
		    // name값 세팅 및 초기화
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].idx']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].idx','value':'0'});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].isChange']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].isChange','value':'N'});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].itemName']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].itemName','value':'','onchange':"javascript:isChange('itemVOList["+childArrayCnt+"].isChange')"});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].itemNameEng']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].itemNameEng','value':'','onchange':"javascript:isChange('itemVOList["+childArrayCnt+"].isChange')"});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].price']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].price','value':'0','onchange':"javascript:isChange('itemVOList["+childArrayCnt+"].isChange')"});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].content']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].content','value':'','onchange':"javascript:isChange('itemVOList["+childArrayCnt+"].isChange')"});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].contentEng']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].contentEng','value':'','onchange':"javascript:isChange('itemVOList["+childArrayCnt+"].isChange')"});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].repTag']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].repTag','value':'','onchange':"javascript:isChange('itemVOList["+childArrayCnt+"].isChange')"});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].hashTagCodes']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].hashTagCodes','value':'','onchange':"javascript:isChange('itemVOList["+childArrayCnt+"].isChange')"});
		    $($("input[name='itemVOList["+(childArrayCnt - 1)+"].imgFile']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].imgFile','value':''});
		    $($("div[name='itemVOList["+(childArrayCnt - 1)+"].itemImgDiv']")[1]).attr({'name':'itemVOList['+childArrayCnt+'].itemImgDiv'});
		    $($("div[name='itemVOList["+(childArrayCnt)+"].itemImgDiv']")[0]).html('');
		    
			
	    }
	});
	
	// SHOP 이미지 추가
	$("#shopImgFile").on('change', function(){
		
		if( $("#shopImgFile").val() == "" ) {
			return;
		}
		
		if($("#shopImgDiv div").size() >= 5){
			alert("이미지는 최대 5장까지 선택할 수 있습니다.");
			$("#shopImgFile").val("");
			return;
		}

		var realPath = $("#shopImgFile").val().split("\\");
		var path = realPath[realPath.length-1];
		var ext = path.split(".");
		var ext2 = ext[ext.length-1].toLowerCase();
		// 확장자 확인 
		if( !(ext2 == "jpg" || ext2 == "jpeg") ) {
			alert("JPG, JPEG 이미지만 선택 가능 합니다.");
			$("#shopImgFile").val("");
			return;
		}
		
		var bitsize = this.files[0].size;
		var maxSize = 1024*10000;
		// 파일 크기 확인
		if( bitsize > maxSize ){
			alert("파일 크기는 10MB 이하만 가능 합니다.");
			$("#shopImgFile").val("");
			return;
		}
		
		var formData = new FormData();
		formData.append("gubun","U_S");
		formData.append("imgFile",$("#shopImgFile")[0].files[0]);
		$.ajax({
			url: '/admin/imageUploadAjax.do',
			processData: false,
			contentType: false,
			data: formData,
			type: 'POST',
	        success: function(result){
	        	var result = $.trim(result);
	        	var append = "";
	        	
	        	if( result == "FAIL" ) {
	        		alert("이미지 업로드에 실패하였습니다. 잠시 후에 시도해주세요.");	
	        	} else {
	        		
	        		$("#shopImgFile").val("");
		        	
	        		//화면 갱신
		        	append += "<div style=\"display:inline-block;position:relative;width:145px;margin-right:3px;margin-bottom:3px;\">";
		        	append += "<input type=\"hidden\" name=\"feedImgIdx\" value=\"0\" />";
		        	append += "<input type=\"hidden\" name=\"imgList\" value=\"" + result + "\" />";
		        	append += "<a class=\"imgDel\"style=\"cursor:pointer;position:absolute;right:0;\"><img src=\"/images/admin/btn/del_btn.gif\" alt=\"삭제\" class=\"delImg\"/></a>";
		        	append += "<img src=\"/common/download.do?gubun=D_S&ufn=" + result + "&thumb=145x81\" style=\"width:145px;\">";
		        	append += "</div>";
		        	$("#shopImgDiv").append(append);
		        	
	        	}
	        },
	        fail: function(){
	        	alert("이미지 업로드에 실패하였습니다. 잠시 후에 시도해주세요.");
	        }
	    });
	});
	
	// ITEM 이미지 추가
	$("#itemDiv").on('change', ".itemImgFile", function(){
		var index = $('#itemDiv table').index($(this).parent().parent().parent().parent());
		
		if( $(this).val() == "" ) {
			return;
		}

		if($(this).next().find("div").size() >= 5){
			alert("이미지는 최대 5장까지 선택할 수 있습니다.");
			$(this).val("");
			return;
		}

		var realPath = $(this).val().split("\\");
		var path = realPath[realPath.length-1];
		var ext = path.split(".");
		var ext2 = ext[ext.length-1].toLowerCase();
		// 확장자 확인 
		if( !(ext2 == "jpg" || ext2 == "jpeg") ) {
			alert("JPG, JPEG 이미지만 선택 가능 합니다.");
			$(this).val("");
			return;
		}
		
		var bitsize = this.files[0].size;
		var maxSize = 1024*10000;
		// 파일 크기 확인
		if( bitsize > maxSize ){
			alert("파일 크기는 10MB 이하만 가능 합니다.");
			$(this).val("");
			return;
		}
		
		$("input[name='itemVOList["+index+"].isChange']").val("Y");
		
		var itemImgFile = $(this);
		var formData = new FormData();
		formData.append("gubun","U_I");
		formData.append("imgFile",this.files[0]);
		$.ajax({
			url: '/admin/imageUploadAjax.do',
			processData: false,
			contentType: false,
			data: formData,
			type: 'POST',
	        success: function(result){
	        	var result = $.trim(result);
	        	var append = "";
	        	
	        	if( result == "FAIL" ) {
	        		alert("이미지 업로드에 실패하였습니다. 잠시 후에 시도해주세요.");	
	        	} else {
	        		
	        		itemImgFile.val("");
		        	
	        		//화면 갱신
		        	append += "<div style=\"display:inline-block;position:relative;width:145px;margin-right:3px;margin-bottom:3px;\">";
		        	append += "<input type=\"hidden\" name=\"feedImgIdx\" value=\"0\" />";
		        	append += "<input type=\"hidden\" name=\"itemVOList["+index+"].imgList\" value=\"" + result + "\" />";
		        	append += "<a class=\"imgDel\"style=\"cursor:pointer;position:absolute;right:0;\"><img src=\"/images/admin/btn/del_btn.gif\" alt=\"삭제\" class=\"delImg\"/></a>";
		        	append += "<img src=\"/common/download.do?gubun=D_I&ufn=" + result + "&thumb=145x81\" style=\"width:145px;\">";
		        	append += "</div>";
		        	$(itemImgFile).next().append(append);
		        	
	        	}
	        },
	        fail: function(){
	        	alert("이미지 업로드에 실패하였습니다. 잠시 후에 시도해주세요.");
	        }
	    });
	});
	
	//SHOP 이미지 삭제
	$('#shopImgDiv').on('click', '.imgDel', function () {
		deleteShopImg($(this));
	});
	
	//ITEM 이미지 삭제
	$('.itemImgDiv').on('click', '.imgDel', function () {
		deleteItemImg($(this));
	});
	
	//아이템 삭제
	$('#itemDiv').on('click', '.itemDel', function () {
		deleteItem($(this));
	});
	
});
// SHOP 이미지 삭제
function deleteShopImg(obj){
	if($($(obj).parent().children()[0]).val() != 0){
		if( confirm("이미 등록되어 있는 SHOP 이미지 정보를 삭제하겠습니까?") ) {
			$.post("/admin/store/shopImgDeleteProc.do",{"idx":$($(obj).parent().children()[0]).val(), "storeIdx":$("input[name='idx']").val()},function(data){
        		data = $.trim(data);
        		if( !data ) {
        			alert("삭제 중 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
        		}
        	});
			$(obj).parent().remove();
		}
	}
	else{
		$(obj).parent().remove();
	}
}
// ITEM 이미지 삭제
function deleteItemImg(obj){
	var index = $('#itemDiv table').index($(obj).parent().parent().parent().parent().parent().parent());
	if($($(obj).parent().children()[0]).val() != 0){
		if( confirm("이미 등록되어 있는 ITEM 이미지 정보를 삭제하겠습니까?") ) {
			$.post("/admin/store/itemImgDeleteProc.do",{"idx":$($(obj).parent().children()[0]).val(), "storeIdx":$("input[name='idx']").val(), "itemIdx":$("input[name='itemVOList["+index+"].idx']").val()},function(data){
        		data = $.trim(data);
        		if( !data ) {
        			alert("삭제 중 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
        		}
        	});
			$(obj).parent().remove();
		}
	}
	else{
		$(obj).parent().remove();
	}
}
// ITEM 삭제
function deleteItem(obj) {
    
	//ITEM 테이블 삭제
   	var index = $('#itemDiv table').index($(obj).parent().parent().parent().parent().parent().parent());
    var childArrayCnt = Number($("#itemDiv")[0].childElementCount);
    if( $("input[name='itemVOList["+index+"].idx']").val() != 0) {
		if( confirm("이미 등록되어 있는 ITEM을 삭제하겠습니까?") ) {
        	// 등록된 ITEM 삭제
        	$.post("/admin/store/itemDeleteProc.do",{"idx":$("input[name='itemVOList["+index+"].idx']").val(),"storeIdx":${storeVO.idx}},function(data){
        		data = $.trim(data);
        		if( !data ) {
        			alert("삭제 중 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
        		}
        	});
        	
        	if(childArrayCnt == 1){
        		$($("#itemDiv").children()[0]).css('display', 'none');
        		$("#itemTitle").css('display', 'none');
        	}
        	else {
	        	// name 값 재조절
		    	for(var i = index + 1; i < childArrayCnt ; i++){
		    		$("input[name='itemVOList["+i+"].idx']").attr('name','itemVOList['+(i-1)+'].idx');
		    		$("input[name='itemVOList["+i+"].isChange']").attr('name','itemVOList['+(i-1)+'].isChange');
		    		$("input[name='itemVOList["+i+"].itemName']").attr({'name':'itemVOList['+(i-1)+'].itemName','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
		    		$("input[name='itemVOList["+i+"].itemNameEng']").attr({'name':'itemVOList['+(i-1)+'].itemNameEng','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
		    	    $("input[name='itemVOList["+i+"].price']").attr({'name':'itemVOList['+(i-1)+'].price','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
		    	    $("input[name='itemVOList["+i+"].content']").attr({'name':'itemVOList['+(i-1)+'].content','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
		    	    $("input[name='itemVOList["+i+"].contentEng']").attr({'name':'itemVOList['+(i-1)+'].contentEng','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
		    	    $("input[name='itemVOList["+i+"].regTag']").attr({'name':'itemVOList['+(i-1)+'].regTag','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
		    	    $("input[name='itemVOList["+i+"].hashTagCodes']").attr({'name':'itemVOList['+(i-1)+'].hashTagCodes','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
		    	    $("input[name='itemVOList["+i+"].imgFile']").attr({'name':'itemVOList['+(i-1)+'].imgFile'});
		    	    $("div[name='itemVOList["+i+"].itemImgDiv']").attr({'name':'itemVOList['+(i-1)+'].itemImgDiv'});
		    	    $("input[name='itemVOList["+i+"].imgList']").attr({'name':'itemVOList['+(i-1)+'].imgList'});
		        
		    	}
		
		        // 삭제
		        $(obj).parent().parent().parent().parent().parent().parent().remove();
        	}
        }
	} else {
		if(childArrayCnt == 1){
       		$($("#itemDiv").children()[0]).css('display', 'none');
       		$("#itemTitle").css('display', 'none');
       	}
       	else {
	       	// name 값 재조절
	    	for(var i = index + 1; i < childArrayCnt ; i++){
	    		$("input[name='itemVOList["+i+"].idx']").attr('name','itemVOList['+(i-1)+'].idx');
	    		$("input[name='itemVOList["+i+"].isChange']").attr('name','itemVOList['+(i-1)+'].isChange');
	    		$("input[name='itemVOList["+i+"].itemName']").attr({'name':'itemVOList['+(i-1)+'].itemName','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
	    		$("input[name='itemVOList["+i+"].itemNameEng']").attr({'name':'itemVOList['+(i-1)+'].itemNameEng','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
	    	    $("input[name='itemVOList["+i+"].price']").attr({'name':'itemVOList['+(i-1)+'].price','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
	    	    $("input[name='itemVOList["+i+"].content']").attr({'name':'itemVOList['+(i-1)+'].content','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
	    	    $("input[name='itemVOList["+i+"].contentEng']").attr({'name':'itemVOList['+(i-1)+'].contentEng','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
	    	    $("input[name='itemVOList["+i+"].regTag']").attr({'name':'itemVOList['+(i-1)+'].regTag','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
	    	    $("input[name='itemVOList["+i+"].hashTagCodes']").attr({'name':'itemVOList['+(i-1)+'].hashTagCodes','onchange':"javascript:isChange('itemVOList["+(i-1)+"].isChange')"});
	    	    $("input[name='itemVOList["+i+"].imgFile']").attr({'name':'itemVOList['+(i-1)+'].imgFile'});
	    	    $("div[name='itemVOList["+i+"].itemImgDiv']").attr({'name':'itemVOList['+(i-1)+'].itemImgDiv'});
	    	    $("input[name='itemVOList["+i+"].imgList']").attr({'name':'itemVOList['+(i-1)+'].imgList'});
	    	}
	
	        // 삭제
	        $(obj).parent().parent().parent().parent().parent().parent().remove();
       	}
	}
}
//수정여부 변경
function isChange(name){
	$("input[name='"+name+"']").val("Y");
}
//비밀번호 초기화
function pwdClick() {
	if( confirm("비밀번호를 초기화 하겠습니까?") ) {
		// 승인
    	$.post("/admin/store/storeUpdatePwdProc.do",{"accId":"${storeVO.storeKeeperId}"},function(data){
    		data = $.trim(data);
    		if( !data ) {
    			alert("비밀번호 초기화 중 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
    		} else{
    			alert("비밀번호가 초기화 되었습니다.");
    		}
    	});
	}
}
// 우편번호 찾기
function execDaumPostcode() {
	var f = document.procForm;
	
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            f.zipCode.value = data.zonecode; //5자리 새우편번호 사용
            f.addr1.value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            f.addr2.focus();
        }
    }).open();
}
//등록
function writeClick() {
	
	var f = document.procForm;
	
	if( !f.cate1Cd.value || f.cate1Cd.value == 0 ) {
		alert("카테고리를 선택하세요.");
		f.cate1Cd.focus();
		return;
	}
	if( !f.storeName.value ) {
		alert("가맹점명을 입력하세요.");
		f.storeName.focus();
		return;
	}
	if( !f.storeNameEng.value ) {
		alert("가맹점명(영문)을 입력하세요.");
		f.storeNameEng.focus();
		return;
	}
	if( !f.storeKeeperName.value ) {
		alert("대표자명을 입력하세요.");
		f.storeKeeperName.focus();
		return;
	}
	<c:if test="${storeVO.idx == 0}">
	if( !f.storeKeeperId.value ) {
		alert("대표 아이디를 입력하세요.");
		f.storeKeeperId.focus();
		return;
	}
	var idReg = /^[a-z]+[a-z0-9]{5,19}$/ig;
    if( !idReg.test( f.storeKeeperId.value ) ) {
        alert("대표 아이디는 영문 또는 영문+숫자 조합, 6~20자리로 입력하세요.");
        f.storeKeeperId.focus();
        return;
    }
	if( f.storeKeeperIdCheck.value != 'Y' ) {
		alert("대표 아이디 중복체크를 하세요.");
		return;
	}
	if( !f.storeKeeperPw.value ) {
		alert("대표 비밀번호를 입력하세요.");
		f.storeKeeperPw.focus();
		return;
	}
	var pwdChk1 = /[A-za-z]/ig;
	var pwdChk2 = /[0-9]/ig;
	if( f.storeKeeperPw.value.length < 8 || !pwdChk1.test( f.storeKeeperPw.value ) || !pwdChk2.test( f.storeKeeperPw.value ) ) {
		alert("비밀번호는 영문+숫자, 8자리 이상 입력하세요.");
		f.storeKeeperPw.focus();
		return;
	}
	</c:if>
	if( !f.storeKeeperPhoneNum1.value || !f.storeKeeperPhoneNum2.value || !f.storeKeeperPhoneNum3.value ) {
		alert("대표 연락처를 입력하세요.");
		f.storeKeeperPhoneNum1.focus();
		return;
	}
	if( isNaN(f.storeKeeperPhoneNum1.value) || isNaN(f.storeKeeperPhoneNum2.value) || isNaN(f.storeKeeperPhoneNum3.value) ) {
		alert("대표 연락처는 숫자로 입력하세요.");
		f.storeKeeperPhoneNum1.value = "";
		f.storeKeeperPhoneNum2.value = "";
		f.storeKeeperPhoneNum3.value = "";
		f.storeKeeperPhoneNum1.focus();
		return;
	}
	if(f.faxNum1.value || f.faxNum2.value || f.faxNum3.value){
		if(!f.faxNum1.value || !f.faxNum2.value || !f.faxNum3.value){
			alert("팩스번호를 입력하세요.");
			f.faxNum1.focus();
			return;
		}
		if( isNaN(f.faxNum1.value) || isNaN(f.faxNum2.value) || isNaN(f.faxNum3.value) ) {
			alert("팩스번호는 숫자로 입력하세요.");
			f.faxNum1.value = "";
			f.faxNum2.value = "";
			f.faxNum3.value = "";
			f.faxNum1.focus();
			return;
		}
	}
	if( !f.phoneNum1.value || !f.phoneNum2.value || !f.phoneNum3.value ) {
		alert("가맹점 연락처를 입력하세요.");
		f.phoneNum1.focus();
		return;
	}
	if( isNaN(f.phoneNum1.value) || isNaN(f.phoneNum2.value) || isNaN(f.phoneNum3.value) ) {
		alert("가맹점 연락처는 숫자로 입력하세요.");
		f.phoneNum1.value = "";
		f.phoneNum2.value = "";
		f.phoneNum3.value = "";
		f.phoneNum1.focus();
		return;
	}
	if( !f.storeKeeperEmail.value ) {
		alert("대표 이메일을 입력하세요.");
		f.storeKeeperEmail.focus();
		return;
	}
	var emailChk1 = /^(\w+)@(\w+)[.](\w+)$/ig;
	var emailChk2 = /^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig;
	if( f.storeKeeperEmail.value ) {
		if( !emailChk1.test( f.storeKeeperEmail.value ) && !emailChk2.test( f.storeKeeperEmail.value ) ) {
			alert("이메일이 형식이 올바르지 않습니다.");
			f.storeKeeperEmail.focus();
			return;
		}
	}
	if( !f.zipCode.value || !f.addr1.value || !f.addr2.value ) {
		alert("주소를 입력하세요.");
		return;
	}
	if( !f.content.value) {
		alert("가맹점 설명을 입력하세요.");
		f.content.focus();
		return;
	}
	if( !f.contentEng.value) {
		alert("가맹점 설명(영문)을 입력하세요.");
		f.contentEng.focus();
		return;
	}
	if( !f.hashTagCodes.value) {
		alert("가맹점 태그를 입력하세요.");
		f.hashTagCodes.focus();
		return;
	}
	for(var i = 0; i < 3; i++){
		if((!$("select[name='storeSnsVOList["+i+"].snsType']").val() && $("input[name='storeSnsVOList["+i+"].snsUrl']").val())
				|| ($("select[name='storeSnsVOList["+i+"].snsType']").val() && !$("input[name='storeSnsVOList["+i+"].snsUrl']").val())){
			alert("SNS 정보를 입력하세요.");
			$("input[name='storeSnsVOList["+i+"].snsUrl']").focus();
			return;
		} else if($("select[name='storeSnsVOList["+i+"].snsType']").val() && $("input[name='storeSnsVOList["+i+"].snsUrl']").val()){
			// url 검사
			if(($("input[name='storeSnsVOList["+i+"].snsUrl']").val().indexOf("http://") == -1) && ($("input[name='storeSnsVOList["+i+"].snsUrl']").val().indexOf("https://") == -1)){
				alert("SNS 링크에 'http://' 혹은 'https://'을 입력하세요.");
				$("input[name='storeSnsVOList["+i+"].snsUrl']").focus();
				return;
			}
		}
	}
	for(var i = 0; i < 3; i++){
		if($("input[name='beaconVOList["+i+"].isChange']").val() == 'Y' && $("input[name='beaconVOList["+i+"].mac']").val()){
			if($("input[name='beaconVOList[" + i + "].isMacCheck']").val() == "N"){
				alert("MAC 중복체크 하세요.");
				$("input[name='beaconVOList[" + i + "].mac']").focus()
				return;
			}
		}
	}
	
	var childArrayCnt = Number($("#itemDiv")[0].childElementCount);
	if($($("#itemDiv").children()[0]).css('display') == 'none'){
		childArrayCnt = childArrayCnt - 1;
	}
	for(var i = 0; i< childArrayCnt; i++){
		if( !$("input[name='itemVOList["+i+"].itemName']").val()) {
			alert("아이템 명을 입력하세요.");
			$("input[name='itemVOList["+i+"].itemName']").focus();
			return;
		}
		if( !$("input[name='itemVOList["+i+"].itemNameEng']").val()) {
			alert("아이템 명(영문)을 입력하세요.");
			$("input[name='itemVOList["+i+"].itemNameEng']").focus();
			return;
		}
		if( !$("input[name='itemVOList["+i+"].price']").val() || $("input[name='itemVOList["+i+"].price']").val() == '0') {
			alert("가격을 입력하세요.");
			$("input[name='itemVOList["+i+"].price']").focus();
			return;
		}
		if( isNaN($("input[name='itemVOList["+i+"].price']").val()) ) {
			alert("가격은 숫자로 입력하세요.");
			$("input[name='itemVOList["+i+"].price']").val("");
			$("input[name='itemVOList["+i+"].price']").focus();
			return;
		}
		if( !$("input[name='itemVOList["+i+"].content']").val()) {
			alert("설명을 입력하세요.");
			$("input[name='itemVOList["+i+"].content']").focus();
			return;
		}
		if( !$("input[name='itemVOList["+i+"].contentEng']").val()) {
			alert("설명(영문)을 입력하세요.");
			$("input[name='itemVOList["+i+"].contentEng']").focus();
			return;
		}
		if( !$("input[name='itemVOList["+i+"].repTag']").val()) {
			alert("대표태그를 입력하세요.");
			$("input[name='itemVOList["+i+"].repTag']").focus();
			return;
		}
		if( !$("input[name='itemVOList["+i+"].hashTagCodes']").val()) {
			alert("태그를 입력하세요.");
			$("input[name='itemVOList["+i+"].hashTagCodes']").focus();
			return;
		}
	}
	if( confirm("STORE를 ${storeVO.idx == 0 ? '등록' : '수정'}하겠습니까?") ) {
		if(childArrayCnt == 0){
			$($("#itemDiv").children()[0]).remove();
		}
		if(f.faxNum1.value || f.faxNum2.value || f.faxNum3.value ){
			f.faxNum.value = f.faxNum1.value + '-' + f.faxNum2.value + '-' + f.faxNum3.value;
		}
		f.storeKeeperPhoneNum.value = f.storeKeeperPhoneNum1.value + '-' + f.storeKeeperPhoneNum2.value + '-' + f.storeKeeperPhoneNum3.value;
		f.phoneNum.value = f.phoneNum1.value + '-' + f.phoneNum2.value + '-' + f.phoneNum3.value;
		f.action = "/admin/store/storeWriteProc.do";
		f.submit();
	}
}
//취소
function cancelClick() {
	history.back();
}
//아이디 중복체크
function idCheckClick() {
	if( !$("input[name='storeKeeperId']").val() ) {
		alert("아이디를 입력하세요.");
		$("input[name='storeKeeperId']").focus();
		return;
	}
	var idReg = /^[a-z]+[a-z0-9]{5,19}$/g;
    if( !idReg.test( $("input[name='storeKeeperId']").val() ) ) {
        alert("아이디는 영문 또는 영문+숫자 조합, 6~20자리로 입력하세요.");
        $("input[name='storeKeeperId']").focus();
        return;
    }
	$.post("/admin/store/storeAccIdCheck.do",{"accId":$("input[name='storeKeeperId']").val()}, function(data) {
		data = $.trim(data);
		$("input[name='storeKeeperIdCheck']").val(data);
		if( data == 'Y' ) {
			alert("사용 가능한 아이디입니다.");
		} else {
			alert("이미 사용중인 아이디입니다.");
		}
	});
}
// 아이디 수정시
function idUpdateEvent() {
	$("input[name='storeKeeperIdCheck']").val("N");
	$("input[name='storeKeeperId']").val(($("input[name='storeKeeperId']").val()).toLowerCase());
}
//MAC 중복체크
function macCheckClick(macNo) {
	if( !$("input[name='beaconVOList[" + macNo + "].mac']").val() ) {
		alert("MAC을 입력하세요.");
		$("input[name='beaconVOList[" + macNo + "].mac']").focus();
		return;
	}
	// 중복체크
	for(var i=0; i<3; i++) {
		if(i == macNo)
			continue;
		if($("input[name='beaconVOList[" + i + "].mac']").val() == $("input[name='beaconVOList[" + macNo + "].mac']").val()) {
			alert("같은 MAC을 등록할 수 없습니다.");
			$("input[name='beaconVOList[" + macNo + "].mac']").focus();
			return;
		}
	}
	$.post("/admin/store/storeMacCheck.do",{"mac":$("input[name='beaconVOList[" + macNo + "].mac']").val(),"storeIdx":${storeVO.idx}}, function(data) {
		var result;
		data = $.trim(data);
		result = data.split(',');
		$("input[name='beaconVOList[" + macNo + "].isMacCheck']").val(result[0]);
		if( result[0] == 'Y' ) {
			alert("사용 가능한 MAC입니다.");
		} else {
			alert("이미 사용중인 MAC입니다.\n사용 중인 가맹점 : "+result[1]);
		}
	});
}
//MAC 수정시
function macUpdate(macNo) {
	$("input[name='beaconVOList[" + macNo + "].isMacCheck']").val("N");
}
</script>
</head>
<body>
<form name="procForm" method="post">
<input type="hidden" name="idx" value="${storeVO.idx}"/>
<input type="hidden" name="storeKeeperIdCheck" value="N"/>
<input type="hidden" name="storeKeeperInfoIsChange" value="N"/>
<input type="hidden" name="mgrIdIsChange" value="N"/>
<input type="hidden" name="storeKeeperPhoneNum" value=""/>
<input type="hidden" name="faxNum" value=""/>
<input type="hidden" name="phoneNum" value=""/>
<div id="warp">
<div class="alphaLayer" style="display:none;">
	<div class="load"></div>
</div>
	<!--헤더영역-->
	<%@include file="/WEB-INF/views/common/include/top.jsp" %>
	<!--헤더영역-->
	
	<div id="container" class="floatBox">
		<!--사이드메뉴영역-->
		<%@include file="/WEB-INF/views/common/include/left.jsp" %>
		<!--사이드메뉴영역-->
		
		<!--컨텐츠영역-->
		<div class="content content_height">
			
			<h3 class="content_tit">
				Store 관리
			</h3>
			
			<div class="floatBox tit_radio">
				<p>등록정보</p>		
			</div>
			<table class="table_view_type01">
				<col width="148px" />
				<col width="*" />
				<col width="148px" />
				<col width="370px" />
				<tbody>
					<tr>
						<th>
							매니저
						</th>
						<td colspan="3">
							<ul class="address_type">
								<li<c:if test="${storeVO.idx != 0}"> class="gap_t"</c:if>>
									<input type="hidden" name="mgrId" value="${storeVO.mgrId}" />
									<div id="mgrInfo">
										<c:choose>
										<c:when test="${storeVO.idx == 0}">
										</c:when>
										<c:when test="${'' ne storeVO.mgrId}">
										${storeVO.mgrId} (${storeVO.mgrName})
										</c:when>
										<c:otherwise>
										-
										</c:otherwise>
										</c:choose>
									</div>
								</li>
								<li<c:if test="${storeVO.idx != 0}"> class="gap_r"</c:if>>
									<a href="/admin/store/storeManagerSearchPop.do" class="manager_pop">매니저 찾기</a>
								</li>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
			
			<div class="floatBox tit_radio" style="float:left;">
				<p>SHOP 기본정보</p>		
			</div>
			<div style="position:relative;float:right;margin:10px;">
				(* 은 필수항목 입니다.)
			</div>
			
			<table class="table_view_type01" style="clear:both;">
				<col width="148px" />
				<col width="*" />
				<col width="148px" />
				<col width="370px" />
				<tbody>
					<tr>
						<th>
							* 카테고리 명
						</th>
						<td colspan="3">
							<select name="cate1Cd" style="width:188px;height:40px;border:solid 1px #ddd;">
								<c:if test="${storeVO.idx == 0}"><option value="0">카테고리 선택</option></c:if>
								<option value="001"<c:if test="${'001' eq storeVO.cate1Cd}"> selected</c:if>>Food</option>
								<option value="002"<c:if test="${'002' eq storeVO.cate1Cd}"> selected</c:if>>Cafe</option>
								<option value="003"<c:if test="${'003' eq storeVO.cate1Cd}"> selected</c:if>>Club/Bar</option>
								<option value="004"<c:if test="${'004' eq storeVO.cate1Cd}"> selected</c:if>>Shopping</option>
								<option value="005"<c:if test="${'005' eq storeVO.cate1Cd}"> selected</c:if>>Furniture</option>
								<option value="006"<c:if test="${'006' eq storeVO.cate1Cd}"> selected</c:if>>Hotel</option>
								<option value="007"<c:if test="${'007' eq storeVO.cate1Cd}"> selected</c:if>>Living/Culture</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							* 가맹점 명
						</th>
						<td colspan="3">
							<ul class="sns_type">
							<li>
							<input type="text" name="storeName" value="${storeVO.storeName}" class="w320" placeholder="국문" maxlength="30" />
							</li>
							<li>
							<input type="text" name="storeNameEng" value="${storeVO.storeNameEng}" class="w320" placeholder="영문" maxlength="30" />
							</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							사업자번호
						</th>
						<td>
							<input type="text" name="businessNum" value="${storeVO.businessNum}" class="w200" maxlength="15" />
						</td>
						<th>
							* 대표자명
						</th>
						<td>
							<input type="text" name="storeKeeperName" value="${storeVO.storeKeeperName}" class="w200" onchange="javascript:isChange('storeKeeperInfoIsChange')" maxlength="10" />
						</td>
					</tr>
					<tr>
						<th>
							* 대표 아이디
						</th>
						<td colspan="3">
							<ul class="address_type">
								<c:choose>
								<c:when test="${storeVO.idx == 0}">
								<li>
									<input type="text" name="storeKeeperId" value="" class="w200" style="ime-mode:disabled;" onkeyup="javascript:idUpdateEvent()" maxlength="20" />
								</li>
								<li class="gap_r">
									<a href="javascript:idCheckClick()">중복확인</a>
								</li>
								</c:when>
								<c:otherwise>
								<li>
									<input type="hidden" name="storeKeeperId" value="${storeVO.storeKeeperId}" />
									${storeVO.storeKeeperId}
								</li>
								</c:otherwise>
								</c:choose>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							* 비밀번호
						</th>
						<td colspan="3">
							<ul class="address_type">
								<li>
								<c:choose>
								<c:when test="${storeVO.idx == 0}">
									<input type="password" name="storeKeeperPw" class="w200" maxlength="20" />
								</c:when>
								<c:otherwise>
									<a href="javascript:pwdClick()" style="width:150px;">비밀번호 초기화 발송</a>
								</c:otherwise>
								</c:choose>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							* 대표 연락처
						</th>
						<td>
							<ul class="phone_list">
								<li>
									<input type="text" name="storeKeeperPhoneNum1" value="${fn:split(storeVO.storeKeeperPhoneNum,'-')[0]}" class="w74" onchange="javascript:isChange('storeKeeperInfoIsChange')" maxlength="4" />
								</li>
								<li class="gap">
									-
								</li>
								<li>
									<input type="text" name="storeKeeperPhoneNum2" value="${fn:split(storeVO.storeKeeperPhoneNum,'-')[1]}" class="w74" onchange="javascript:isChange('storeKeeperInfoIsChange')" maxlength="4" />
								</li>
								<li class="gap">
									-
								</li>
								<li>
									<input type="text" name="storeKeeperPhoneNum3" value="${fn:split(storeVO.storeKeeperPhoneNum,'-')[2]}" class="w74" onchange="javascript:isChange('storeKeeperInfoIsChange')" maxlength="4" />
								</li>
							</ul>
						</td>
						<th>
							팩스번호
						</th>
						<td>
							<ul class="phone_list">
								<li>
									<input type="text" name="faxNum1" value="${fn:split(storeVO.faxNum,'-')[0]}" class="w74" maxlength="4" />
								</li>
								<li class="gap">
									-
								</li>
								<li>
									<input type="text" name="faxNum2" value="${fn:split(storeVO.faxNum,'-')[1]}" class="w74" maxlength="4" />
								</li>
								<li class="gap">
									-
								</li>
								<li>
									<input type="text" name="faxNum3" value="${fn:split(storeVO.faxNum,'-')[2]}" class="w74" maxlength="4" />
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							* 가맹점 연락처
						</th>
						<td>
							<ul class="phone_list">
								<li>
									<input type="text" name="phoneNum1" value="${fn:split(storeVO.phoneNum,'-')[0]}" class="w74" maxlength="4" />
								</li>
								<li class="gap">
									-
								</li>
								<li>
									<input type="text" name="phoneNum2" value="${fn:split(storeVO.phoneNum,'-')[1]}" class="w74" maxlength="4" />
								</li>
								<li class="gap">
									-
								</li>
								<li>
									<input type="text" name="phoneNum3" value="${fn:split(storeVO.phoneNum,'-')[2]}" class="w74" maxlength="4" />
								</li>
							</ul>
						</td>
						<th>
							* 대표이메일
						</th>
						<td>
							<ul class="mail_type">
								<li>
									<input type="text" name="storeKeeperEmail" value="${storeVO.storeKeeperEmail}" class="w200" onchange="javascript:isChange('storeKeeperInfoIsChange')" maxlength="45" />
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							* 주소
						</th>
						<td colspan="3">
							<ul class="address_type">
								<li>
									<input type="text" name="zipCode" value="${storeVO.zipCode}" class="w150" onclick="javascript:execDaumPostcode()" readonly/>
								</li>
								<li class="gap_r">
									<a href="javascript:execDaumPostcode()">우편번호</a>
								</li>
								<li class="gap_t">
									<input type="text" name="addr1" value="${storeVO.addr1}" class="w760" onclick="javascript:execDaumPostcode()" readonly/>
								</li>
								<li class="gap_t">
									<input type="text" name="addr2" value="${storeVO.addr2}" class="w760" maxlength="80"/>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							주차정보
						</th>
						<td>
							<ul class="sns_type">
								<li>
									<input type="text" name="parkInfo" value="${storeVO.parkInfo}" class="w200" placeholder="국문" maxlength="40"/>
								</li>
								<li>
									<input type="text" name="parkInfoEng" value="${storeVO.parkInfoEng}" class="w200" placeholder="영문" maxlength="40"/>
								</li>
							</ul>
						</td>
						<th>
							휴무 안내
						</th>
						<td>
							<ul class="sns_type">
								<li>
									<input type="text" name="closeInfo" value="${storeVO.closeInfo}" class="w200" placeholder="국문" maxlength="40"/>
								</li>
								<li>
									<input type="text" name="closeInfoEng" value="${storeVO.closeInfoEng}" class="w200" placeholder="영문" maxlength="40"/>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							이용시간
						</th>
						<td colspan="3">
							<ul class="time_type">
								<li>
									<select name="businessStartHour" style="width:88px;height:40px;border:solid 1px #ddd;">
										<c:forEach var="businessStartHour" begin="00" end="23" varStatus="status">
										<c:set var="businessStartHour" value="${businessStartHour < 10 ? 0 : null}${businessStartHour}"/>
										<option value="${businessStartHour}" <c:if test="${businessStartHour eq storeVO.businessStartHour}">selected</c:if>>${businessStartHour}</option>
										</c:forEach>
									</select>
									<span>시 ~</span>
								</li>
								<li>
									<select name="businessEndHour" style="width:88px;height:40px;border:solid 1px #ddd;">
										<c:forEach var="businessEndHour" begin="00" end="23" varStatus="status">
										<c:set var="businessEndHour" value="${businessEndHour < 10 ? 0 : null}${businessEndHour}"/>
										<option value="${businessEndHour}" <c:if test="${businessEndHour eq storeVO.businessEndHour}">selected</c:if>>${businessEndHour}</option>
										</c:forEach>
									</select>
									<span>시</span>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							Shop 이미지
						</th>
						<td colspan="3">
							<p>
								<input type="file" id="shopImgFile" name="imgFile" value="" class="w200" /> (1080px * 600px, JPG, JPEG만 가능)
								<div id="shopImgDiv" style="margin:5px 0;">
								<c:forEach var="shopImgVO" items="${shopImgVOList}" varStatus="status">
									<div style="display:inline-block;position:relative;width:145px;margin-right:3px;margin-bottom:3px;">
										<input type="hidden" name="feedImgIdx" value="${shopImgVO.idx}" />
										<input type="hidden" name="imgList" value="${shopImgVO.imgUrl}" />
										<a class="imgDel" style="cursor:pointer;position:absolute;right:0;"><img src="/images/admin/btn/del_btn.gif" alt="삭제" class="delImg"/></a>
										<img src="/common/download.do?gubun=D_S&ufn=${shopImgVO.imgUrl}&thumb=145x81" style="width:145px;">
									</div>
								</c:forEach>
								</div>
							</p>
						</td>
					</tr>
					<tr>
						<th>
							* 가맹점 설명
						</th>
						<td colspan="3">
							<ul class="sns_type">
								<li>
									<input type="text" name="content" value="${shopVO.content}" class="w760" placeholder="국문" />
								</li>
								<li>
									<input type="text" name="contentEng" value="${shopVO.contentEng}" class="w760" placeholder="영문" />
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							* 태그
						</th>
						<td colspan="3">
							<input type="text" name="hashTagCodes" value="${shopVO.hashTagCodes}" placeholder="태그는 ,로 구분해 주세요." maxlength="100" />
						</td>
					</tr>
					<tr>
						<th>
							SNS 링크
						</th>
						<td colspan="3">
							<ul class="sns_type">
								<c:forEach var="storeSnsVO" items="${storeSnsVOArray.storeSnsVOList}" varStatus="status">
								<li>
									<select name="storeSnsVOList[${status.index}].snsType" style="width:100px;height:40px;border:solid 1px #ddd;">
										<option value="">선택하세요</option>
										<option value="F"<c:if test="${'F' eq storeSnsVO.snsType}"> selected</c:if>>Facebook</option>
										<option value="I"<c:if test="${'I' eq storeSnsVO.snsType}"> selected</c:if>>Instagram</option>
										<option value="K"<c:if test="${'K' eq storeSnsVO.snsType}"> selected</c:if>>KakaoStory</option>
									</select>
									<input type="text" name="storeSnsVOList[${status.index}].snsUrl" value="${storeSnsVO.snsUrl}" class="w320" placeholder="URL을 입력해 주세요." maxlength="80"/>
								</li>
								</c:forEach>
								<c:forEach var="i" begin="${fn:length(storeSnsVOArray.storeSnsVOList)}" end="2" varStatus="status">
								<li>
									<select name="storeSnsVOList[${i}].snsType" style="width:100px;height:40px;border:solid 1px #ddd;">
										<option value="">선택하세요</option>
										<option value="F">Facebook</option>
										<option value="I">Instagram</option>
										<option value="K">KakaoStory</option>
									</select>
									<input type="text" name="storeSnsVOList[${i}].snsUrl" value="" class="w320" placeholder="URL을 입력해 주세요." maxlength="80" />
								</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							MAC 주소
						</th>
						<td colspan="3">
							<ul class="sns_type">
								<c:forEach var="beaconVO" items="${beaconVOArray.beaconVOList}" varStatus="status">
								<li>
									<input type="hidden" name="beaconVOList[${status.index}].idx" value="${beaconVO.idx}"/>
									<input type="hidden" name="beaconVOList[${status.index}].isChange" value="N" />
									<input type="hidden" name="beaconVOList[${status.index}].isMacCheck" value="N" />
									<span>
										<input type="text" name="beaconVOList[${status.index}].mac" value="${beaconVO.mac}" class="w320" onchange="javascript:isChange('beaconVOList[${status.index}].isChange'); macUpdate(${status.index});" placeholder=":은 없이 입력" maxlength="12" />
									</span>
									<span style="margin-left:15px;">
										<a href="javascript:macCheckClick(${status.index})">중복확인</a>
									</span>
								</li>
								</c:forEach>
								<c:forEach var="i" begin="${fn:length(beaconVOArray.beaconVOList)}" end="2" varStatus="status">
								<li>
									<input type="hidden" name="beaconVOList[${i}].idx" value="0"/>
									<input type="hidden" name="beaconVOList[${i}].isChange" value="N" />
									<input type="hidden" name="beaconVOList[${i}].isMacCheck" value="N" />
									<span>
										<input type="text" name="beaconVOList[${i}].mac" value="" class="w320" onchange="javascript:isChange('beaconVOList[${i}].isChange'); macUpdate(${status.index});" placeholder=":은 없이 입력" maxlength="12" />
									</span>
									<span style="margin-left:15px;">
										<a href="javascript:macCheckClick(${i})">중복확인</a>
									</span>
								</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<th>
							스타일
						</th>
						<td colspan="3">
							<ul class="sns_type">
								<c:forEach var="styleStdVO" items="${styleStdVOList}" varStatus="status">
								<input type="checkbox" id="styleCode${styleStdVO.code}" name="styleCodes" value="${styleStdVO.code}"/><label for="styleCode${styleStdVO.code}">${styleStdVO.name}</label> &nbsp;
								</c:forEach>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
			
			<div class="floatBox tit_radio" id="itemTitle" style="display:none">
				<p>ITEM</p>		
			</div>
			<div id="itemDiv">
				<c:forEach var="itemVO" items="${itemVOArray.itemVOList}" varStatus="status">
				<table class="table_view_type01">
					<col width="148px" />
					<col width="*" />
					<col width="148px" />
					<col width="370px" />
					<thead>
						<tr>
							<td>
								<input type="hidden" name="itemVOList[${status.index}].idx" value="${itemVO.idx}"/>
								<input type="hidden" name="itemVOList[${status.index}].isChange" value="N"/>
							</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>
								아이템 이미지
							</th>
							<td colspan="3">
								<input type="file" name="itemVOList[${status.index}].imgFile" value="" class="w200 itemImgFile" /> (1080px * 600px, JPG, JPEG만 가능)
								<div name="itemVOList[${status.index}].itemImgDiv" class="itemImgDiv" style="margin:5px 0;">
								<c:forEach var="itemImgVO" items="${itemVO.itemImgVOList}" varStatus="itemImgStatus">
									<div style="display:inline-block;position:relative;width:145px;margin-right:3px;margin-bottom:3px;">
										<input type="hidden" name="itemImgIdx" value="${itemImgVO.idx}" />
										<input type="hidden" name="itemVOList[${status.index}].imgList" value="${itemImgVO.imgUrl}" />
										<a class="imgDel" style="cursor:pointer;position:absolute;right:0;"><img src="/images/admin/btn/del_btn.gif" alt="삭제" class="delImg"/></a>
										<img src="/common/download.do?gubun=D_I&ufn=${itemImgVO.imgUrl}&thumb=145x81" style="width:145px;">
									</div>
								</c:forEach>
								</div>
							</td>
						</tr>
						<tr>
							<th>
								* 아이템명
							</th>
							<td>
								<ul class="sns_type">
									<li>
										<input type="text" name="itemVOList[${status.index}].itemName" value="${itemVO.itemName}" class="w200" onchange="javascript:isChange('itemVOList[${status.index}].isChange')" placeholder="국문" maxlength="45" />
									</li>
									<li>
										<input type="text" name="itemVOList[${status.index}].itemNameEng" value="${itemVO.itemNameEng}" class="w200" onchange="javascript:isChange('itemVOList[${status.index}].isChange')" placeholder="영문" maxlength="45" />
									</li>
								</ul>
							</td>
							<th>
								* 가격
							</th>
							<td>
								<input type="text" name="itemVOList[${status.index}].price" value="${itemVO.price}" class="w130" onchange="javascript:isChange('itemVOList[${status.index}].isChange')" maxlength="10" /> <span>won</span>
							</td>
						</tr>
						<tr>
							<th>
								* 설명
							</th>
							<td colspan="3">
								<ul class="sns_type">
									<li>
										<input type="text" name="itemVOList[${status.index}].content" value="${itemVO.content}" class="w760" onchange="javascript:isChange('itemVOList[${status.index}].isChange')" placeholder="국문" />
									</li>
									<li>
										<input type="text" name="itemVOList[${status.index}].contentEng" value="${itemVO.contentEng}" class="w760" onchange="javascript:isChange('itemVOList[${status.index}].isChange')" placeholder="영문" />
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th>
								* 대표태그
							</th>
							<td colspan="3">
								<input type="text" name="itemVOList[${status.index}].repTag" value="${itemVO.repTag}" onchange="javascript:isChange('itemVOList[${status.index}].isChange')" maxlength="45" />
							</td>
						</tr>
						<tr>
							<th>
								* 태그
							</th>
							<td colspan="3">
								<input type="text" name="itemVOList[${status.index}].hashTagCodes" value="${itemVO.hashTagCodes}" onchange="javascript:isChange('itemVOList[${status.index}].isChange')" placeholder="태그는 ,로 구분해 주세요." maxlength="100" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<ul class="btn_area" style="margin-bottom:10px;">
									<li class="left side_btn">
										<a class="btn_type03 itemDel" style="cursor:pointer;">아이템 삭제</a>
									</li>
								</ul>
							</td>
						</tr>
					</tbody>
				</table>
				</c:forEach>
				
				<c:if test="${fn:length(itemVOArray.itemVOList) == 0}">
				<table class="table_view_type01" style="display:none">
					<col width="148px" />
					<col width="*" />
					<col width="148px" />
					<col width="370px" />
					<thead>
						<tr>
							<td>
								<input type="hidden" name="itemVOList[0].idx" value="0"/>
								<input type="hidden" name="itemVOList[0].isChange" value="N"/>
							</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>
								아이템 이미지
							</th>
							<td colspan="3">
								<input type="file" name="itemVOList[0].imgFile" value="" class="w200 itemImgFile" /> (1080px * 600px)
								<div name="itemVOList[0].itemImgDiv" class="itemImgDiv" style="margin:5px 0;">
								</div>
							</td>
						</tr>
						<tr>
							<th>
								* 아이템명
							</th>
							<td>
								<ul class="sns_type">
									<li>
										<input type="text" name="itemVOList[0].itemName" value="" class="w200" onchange="javascript:isChange('itemVOList[0].isChange')" placeholder="국문" maxlength="45" />
									</li>
									<li>
										<input type="text" name="itemVOList[0].itemNameEng" value="" class="w200" onchange="javascript:isChange('itemVOList[0].isChange')" placeholder="영문" maxlength="45" />
									</li>
								</ul>
							</td>
							<th>
								* 가격
							</th>
							<td>
								<input type="text" name="itemVOList[0].price" value="0" class="w130" onchange="javascript:isChange('itemVOList[0].isChange')" maxlength="10" /> <span>won</span>
							</td>
						</tr>
						<tr>
							<th>
								* 설명
							</th>
							<td colspan="3">
								<ul class="sns_type">
									<li>
										<input type="text" name="itemVOList[0].content" value="" class="w760" onchange="javascript:isChange('itemVOList[0].isChange')" placeholder="국문" />
									</li>
									<li>
										<input type="text" name="itemVOList[0].contentEng" value="" class="w760" onchange="javascript:isChange('itemVOList[0].isChange')" placeholder="영문" />
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th>
								* 대표태그
							</th>
							<td colspan="3">
								<input type="text" name="itemVOList[0].repTag" value="" onchange="javascript:isChange('itemVOList[0].isChange')" maxlength="45" />
							</td>
						</tr>
						<tr>
							<th>
								* 태그
							</th>
							<td colspan="3">
								<input type="text" name="itemVOList[0].hashTagCodes" value="" onchange="javascript:isChange('itemVOList[0].isChange')" placeholder="태그는 ,로 구분해 주세요." maxlength="100" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<ul class="btn_area" style="margin-bottom:10px;">
									<li class="left side_btn">
										<a class="btn_type03 itemDel" style="cursor:pointer;">아이템 삭제</a>
									</li>
								</ul>
							</td>
						</tr>
					</tbody>
				</table>
				</c:if>
			</div>
			<ul class="btn_area" style="margin-bottom:40px;">
				<li class="right">
					<a class="btn_type03 itemAdd" style="cursor:pointer;">아이템 추가</a>
				</li>
			</ul>
			
			<ul class="btn_area">
				<li class="right side_btn">
					<a href="javascript:writeClick()" class="btn_type02">확인</a> &nbsp;
					<a href="javascript:cancelClick()" class="btn_type01">취소</a>
				</li>
			</ul>
			
		</div>
		<!--컨텐츠영역-->
		
	</div>
	
</div>
<script type="text/javascript">
$(document).ready(function() {

	$(".manager_pop").on("click", function(event){
		event.preventDefault();
		var popUrl = "/admin/store/storeManagerSearchPop.do";
		var popOption = "width=520, height=600, resizable=no, scrollbars=yes, status=no;";
		window.open(popUrl,"",popOption);
	});
});
</script>
</form>
</body>
</html>