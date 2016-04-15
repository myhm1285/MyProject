<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
	var valFormatArray = new Array();
	$(document).ready(function(){
		
		// AJAX 동기 처리
		$.ajaxSetup({ async:false });
		
		// 캠페인 목록 로드
		loadCampaignList();
		$("select[name='camIdx'] option[value=${msgMasterVO.camIdx}]").prop("selected",true);
		
	});
	
	// 캠페인 목록 로드
	function loadCampaignList(){
		
		$("select[name='camIdx'] option").remove();
		$("select[name='camIdx']").append("<option value='0'>선택하세요.</option>");
		
		$.post("/campaign/campaignSelectAjax.do",null,function(data){
			var result = data.campaignMasterVOList;
			if(result != undefined){
				for(var i=0; i<result.length; i++){
					$("select[name='camIdx']").append("<option value='"+result[i].camIdx+"'>"+result[i].camNm+"</option>");
				}
			}
		});
	}
	
	// 메시지 내용 팝업
	function msgPopClick(type) {
		window.open("/message/messageWritePop.do?type="+type,
				"messageWritePop", "width=850,height=400,scrollbars=yes,resizable=no,fullscreen=no");
	}
	
	// 등록
	function writeClick() {
		if($("input[name=title]").val() == ""){
			alert("제목을 입력하세요.");
			$("input[name=title]").focus();
			return;
		}
		if($("select[name=camIdx]").val() == 0){
			alert("캠페인을 선택하세요.");
			$("select[name=camIdx]").focus();
			return;
		}
		if($("textarea[name=fullMsg]").val() == 0){
			alert("메시지 내용을 입력하세요.");
			$("textarea[name=fullMsg]").focus();
			return;
		}
		
		if (!confirm("메시지를 등록하겠습니까?")) {
			return;
		}
		// 폼 데이터
		var formData = {
				"title":$("input[name=title]").val(),
				"camIdx":$("select[name=camIdx]").val(),
				"fullMsg":$("textarea[name=fullMsg]").val()
		};
		$.ajax({
		      url: "/message/messageWriteProc.do",
		      method: "post",
		      type: "json",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(formData),
		      success: function(result) {
		    	  result = $.trim(result);
		    	  if( result == "Y" ) {
						alert("등록되었습니다.");
						location.href="/message/messageList.do";
					} else {
						alert("등록 중 오류가 발생하였습니다.");
					}
		      }
		  });
	}
	
	// 수정
	function updateClick() {
		if($("input[name=title]").val() == ""){
			alert("제목을 입력하세요.");
			$("input[name=title]").focus();
			return;
		}
		
		if($("select[name=camIdx]").val() == 0){
			alert("캠페인을 선택하세요.");
			$("select[name=camIdx]").focus();
			return;
		}
		if($("textarea[name=fullMsg]").val() == 0){
			alert("메시지 내용을 입력하세요.");
			$("textarea[name=fullMsg]").focus();
			return;
		}
		
		if (!confirm("메시지를 수정하겠습니까?")) {
			return;
		}
		// 폼 데이터
		var formData = {
				"msgIdx":$("input[name=msgIdx]").val(),
				"title":$("input[name=title]").val(),
				"camIdx":$("select[name=camIdx]").val(),
				"fullMsg":$("textarea[name=fullMsg]").val()
		};
		$.ajax({
		      url: "/message/messageUpdateProc.do",
		      method: "post",
		      type: "json",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(formData),
		      success: function(result) {
		    	  result = $.trim(result);
		    	  if( result == "Y" ) {
						alert("수정되었습니다.");
						location.href="/message/messageList.do";
					} else {
						alert("수정 중 오류가 발생하였습니다.");
					}
		      }
		  });
	}
	
	// 삭제
	function deleteClick() {
		if (!confirm("메시지를 삭제하겠습니까?")) {
			return;
		}
		// 폼 데이터
		var formData = {
				"msgIdx":$("input[name=msgIdx]").val(),
				"camIdx":$("select[name=camIdx]").val()
		};
		$.post("/message/messageDeleteProc.do",formData,function(result){
			result = $.trim(result);
			if( result == "Y" ) {
				alert("삭제되었습니다.");
				location.href="/message/messageList.do";
			} else if(result == "D") {
				alert("삭제가 불가능 합니다. 사유는 아래와 같습니다.\n\n1. 발송되지 않은 예약 메시지에서 사용중인 캠페인일 경우.\n2. 배치에서 사용중인 캠페인일 경우.\n\n위 사항을 확인 후 다시 시도해 주세요.");
			} else {
				alert("삭제 중 오류가 발생하였습니다.");
			}
		});
	}
	// 목록
	function cancelClick() {
		location.href = "/message/messageList.do";
	}
</script>
</head>
<body>
	<form name="procForm" method="post">
	<input type="hidden" name="msgIdx" value="${not empty msgMasterVO.msgIdx ? msgMasterVO.msgIdx : 0}"/>
		<div id="skipNav">
			<a href="#gnb" title="본문바로가기">주메뉴바로가기</a>
			<a href="#contWrap" title="본문바로가기">본문바로가기</a>
		</div>
		<div class="wrap">
		<jsp:include page="/WEB-INF/views/include/top.jsp"/>
		<div class="nistWrap">
		<jsp:include page="/WEB-INF/views/include/left.jsp"/>
		<!-- contents -->
			<div id="contWrap">
			  <div class="content">
				<!-- pagetitle -->
				<div class="pageInfo">
				  <h4>메시지</h4>
				  <div class="linemap"> <a href="#">HOME</a><img src="/images/common/icon_allow.png" alt="" /><a href="#">매칭 푸시 설정</a><img src="/images/common/icon_allow.png" alt="" /><em>메시지</em> </div>
				</div>
				<!-- //pagetitle end --> 
				<div class="setion">
					<h5>ㆍ${not empty msgMasterVO.msgIdx ? "조회 및 편집" : "등록"}</h5>
				</div>
				<div class="setion">
				  <div class="viewTable">
					  <table width="100%">
						<colgroup>
						  <col width="10%" />
						  <col width="15%" />
						  <col />
						</colgroup>
						<tbody>
						  <tr>
							<th rowspan="2">메시지<br/>정보</th>
							<th>제목<font color="#ff0000"> *</font></th>
							<td class="longField"><input type="text" name="title" value="${msgMasterVO.title}" style="margin:0 5px;width:750px;" maxlength="180" /></td>
						  </tr>
						  <tr>
						  	<th>캠페인<font color="#ff0000"> *</font></th>
						  	<td class="longField">
						  	<select name="camIdx" style="margin:0 5px;width:300px;">
							  	<option value="0">선택하세요.</option>
						  	</select>
						  	</td>
					  	  </tr>
						  <tr>
						  	<th colspan="3">메시지 내용</th>
					  	  </tr>
						  <tr>
						  	<th colspan="3">
						  	<input type="button" value="JSON형태로 사용하기" onclick="javascript:msgPopClick('json');" style="width:300px;cursor:pointer;background:#434343;color:#fff;" />
						  	<input type="button" value="XML형태로 사용하기" onclick="javascript:msgPopClick('xml');" style="width:300px;cursor:pointer;background:#434343;color:#fff;" />
						  	</td>
					  	  </tr>
						  <tr>
						  	<td colspan="3" class="longField"><textarea name="fullMsg" style="margin:0 5px;width:98%;height:100px" maxlength="1900" >${msgMasterVO.fullMsg}</textarea></td>
					  	  </tr>
					  	</tbody>
					  </table>
				  </div>
				</div>
				<div class="mt20 cbo fl">
				  <input type="button" value="목록" onclick="javascript:cancelClick();" style="width:80px;cursor:pointer;background:#434343;color:#fff;" />
				</div>
				<div class="mt20 mb60 fr">
				<c:choose>
				<c:when test="${not empty msgMasterVO.camIdx}">
				  <input type="button" value="수정" onclick="javascript:updateClick();" style="width:80px;cursor:pointer;background:#434343;color:#fff;"/>
				  <input type="button" value="삭제" onclick="javascript:deleteClick();" style="width:80px;cursor:pointer;background:#434343;color:#fff;"/>
				</c:when>
				<c:otherwise>
				  <input type="button" value="등록" onclick="javascript:writeClick();" style="width:80px;cursor:pointer;background:#434343;color:#fff;"/>
				</c:otherwise>
				</c:choose>
				</div>
			  </div>
			</div>
			<!-- //nistWrap end -->
		  </div>
		</div>
	</form>
</body>
</html>