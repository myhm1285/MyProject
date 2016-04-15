<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
	$(document).ready(function(){
		// AJAX 동기 처리
		$.ajaxSetup({ async:false });
		
		// KEY 삭제
	    $(".msgKey").on('click', '.msgKeyDelBtn', function() {
	    	msgKeyDel($(this));
	    });
	});
	
	// KEY 추가
	function msgKeyAddClick() {
		add_html = $(".msgKeyHidden tr").clone();
		add_html.show();
        $(".msgKey").append(add_html);
	}
    // KEY 삭제 함수
    function msgKeyDel(obj) {
    	$(obj).parent().parent().remove();
    }
	// 등록
	function writeClick() {
		if($("input[name=type]").val() == "XML"){
			if($("input[name=msgMainKey]").val() == ""){
				alert("메시지 메인 KEY를 입력하세요.");
				$("input[name=msgMainKey]").focus();
				return;
			}
			for(var i=0; i<$("input[name=msgMainKey]").val().length; i++) {
				if(i == 0) {
					if(($("input[name=msgMainKey]").val().charCodeAt(i) < 65 || $("input[name=msgMainKey]").val().charCodeAt(i) > 90) &&
							($("input[name=msgMainKey]").val().charCodeAt(i) < 97 || $("input[name=msgMainKey]").val().charCodeAt(i) > 122)){
						alert("메시지 메인 KEY값의 첫 글자는 영어로 입력하세요.");
						$("input[name=msgMainKey]").focus();
						return;
					}
				}
				else {
					if(($("input[name=msgMainKey]").val().charCodeAt(i) < 47 || $("input[name=msgMainKey]").val().charCodeAt(i) > 57) &&
							($("input[name=msgMainKey]").val().charCodeAt(i) < 65 || $("input[name=msgMainKey]").val().charCodeAt(i) > 90) &&
							($("input[name=msgMainKey]").val().charCodeAt(i) < 97 || $("input[name=msgMainKey]").val().charCodeAt(i) > 122)){
						alert("메시지 메인 KEY값의 첫 글자는 영어로 입력하세요.");
						$("input[name=msgMainKey]").focus();
						return;
					}
				}
			}
		}
		var msgKeyVOArray = new Array();
		var isBlankCondition = false;
		var isSameKey = false;
		var isValidKey = true;
		$(".msgKey tr").each(function(index,value){
			var keyCd = $(value).find(":text").eq(0).val();
			var val = $(value).find(":text").eq(1).val();
			
			if(keyCd == "" || val == ""){
				isBlankCondition = true;
				return false;
			}
			if($("input[name=type]").val() == "XML"){
				// 키값 체크(XML)
				for(var i=0; i<keyCd.length; i++){
					if(i == 0){
						if((keyCd.charCodeAt(i) < 65 || keyCd.charCodeAt(i) > 90) &&
								(keyCd.charCodeAt(i) < 97 || keyCd.charCodeAt(i) > 122)){
							isValidKey = false;
							alert("KEY값의 첫 글자는 영어로 입력하세요.");
							return false;
						}
					} else {
						if((keyCd.charCodeAt(i) < 47 || keyCd.charCodeAt(i) > 57) &&
								(keyCd.charCodeAt(i) < 65 || keyCd.charCodeAt(i) > 90) &&
								(keyCd.charCodeAt(i) < 97 || keyCd.charCodeAt(i) > 122)){
							isValidKey = false;
							alert("KEY값은 영어와 숫자로만 입력하세요.");
							return false;
						}
					}
				}
			}
			// 중복 키값 제거
			for(var i = index + 1 ;i < $(".msgKey tr").size(); i++){
				if($(".msgKey tr").eq(i).find(":text").eq(0).val() == keyCd){
					isSameKey = true;
					return false;
				}
			}
			msgKeyVOArray.push({
				"keyCd":keyCd,
				"val":val
			});
		});
		
		if(isBlankCondition) {
			alert("메시지 KEY 정보를 모두 입력하세요.");
			return;
		}
		if(!isValidKey) {
			return;
		}
		if(isSameKey) {
			alert("중복된 이름의 KEY는 사용할 수 없습니다.");
			return;
		}
		
		$.ajax({
		      url: "/message/messageWritePopForChangeType.do",
		      method: "post",
		      type: "json",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify({type:$("input[name='type']").val(),msgKeyVOList:msgKeyVOArray,msgMainKey:$("input[name='msgMainKey']").val()}),
		      success: function(data) {
		    	  if(data != 'ERROR'){
			    	  opener.$("textarea[name='fullMsg']").text(data);
			    	  window.close();
		    	  } else {
		    		  alert('메시지 생성에 실패하였습니다.');
		    	  }
		      }
		  });
	}
</script>
</head>
<body style="background:#fff;min-width:800px;width:800px;">
	<form name="procForm" method="post">
	<input type="hidden" name="type" value="${fn:toUpperCase(param.type)}"/>
		<div class="wrap">
		<div class="nistWrap" style="min-width:800px;width:800px;">
		<!-- contents -->
			<div id="contWrap" style="min-width:800px;width:800px;">
			  <div class="content" style="min-width:800px;width:800px;">
				<div class="setion">
					<h5>ㆍ메시지 내용 - ${fn:toUpperCase(param.type)}</h5>
				</div>
				<div class="setion">
				  <div class="viewTable">
					  <table width="100%">
						<colgroup>
						  <col />
						  <col />
						  <col width="113px"/>
						</colgroup>
						<tbody>
						  <tr>
						  	<th>메시지 메인 KEY<c:if test="${'XML' eq fn:toUpperCase(param.type)}"><font color="#ff0000"> *</font></c:if></th>
						  	<td class="longField" colspan="2"><input type="text" name="msgMainKey" value="" style="margin:0 5px;width:400px;" maxlength="180" /></td>
					  	  </tr>
						</tbody>
						<tbody>
						  <tr>
						  	<th>KEY<font color="#ff0000"> *</font></th>
						  	<th>VALUE<font color="#ff0000"> *</font></th>
						  	<th>관리</th>
					  	  </tr>
					  	</tbody>
					  	<tbody class="msgKeyHidden" style="display:none;">
						<tr>
						  	<td class="longField"><input type="text" name="keyCd" value="" style="margin:0 5px;width:300px;" maxlength="180" /></td>
						  	<td class="longField"><input type="text" name="val" value="" style="margin:0 5px;width:300px;" maxlength="180" /></td>
						  	<td><input type="button" class="msgKeyDelBtn" value="삭제" style="width:80px;cursor:pointer;background:#434343;color:#fff;"/></td>
						</tr>
					  	</tbody>
					  	<tbody class="msgKey">
					  	<tr>
						  	<td class="longField"><input type="text" name="keyCd" value="" style="margin:0 5px;width:300px;" maxlength="180" /></td>
						  	<td class="longField"><input type="text" name="val" value="" style="margin:0 5px;width:300px;" maxlength="180" /></td>
						  	<td>-</td>
						</tr>
					  	</tbody>
					  </table>
				  </div>
				  <div class="mt20 mb20 fr">
				  	<input type="button" value="KEY 추가" onclick="javascript:msgKeyAddClick();" style="width:80px;cursor:pointer;background:#434343;color:#fff;" />
				  </div>
				</div>
				<div class="mt20 mb60 cr fr">
				  <input type="button" value="확인" onclick="javascript:writeClick();" style="width:80px;cursor:pointer;background:#434343;color:#fff;"/>
				  <input type="button" value="취소" onclick="window.close();" style="width:80px;cursor:pointer;background:#434343;color:#fff;"/>
				</div>
			  </div>
			</div>
			<!-- //nistWrap end -->
		  </div>
		</div>
	</form>
</body>
</html>